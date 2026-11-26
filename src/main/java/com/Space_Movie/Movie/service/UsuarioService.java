
package com.Space_Movie.Movie.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import com.Space_Movie.Movie.DTO.UsuarioDTO;
import com.Space_Movie.Movie.Model.TipoUsuario;
import com.Space_Movie.Movie.Model.Usuario;
import com.Space_Movie.Movie.Repository.TipoUsuarioRepository;
import com.Space_Movie.Movie.Repository.UsuarioRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
@Slf4j
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TipoUsuarioRepository tipoUsuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<UsuarioDTO> obtenerUsuarios() {
        log.info("Obteniendo todos los usuarios");

        return usuarioRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .toList();
    }

    public UsuarioDTO obtenerUsuarioPorId(Integer id) {
        return usuarioRepository.findById(id)
                .map(this::convertirADTO)
                .orElseThrow(() -> new RuntimeException(
                        "Usuario no encontrado con la ID: " + id));
    }

    @Transactional
    public UsuarioDTO guardarUsuario(UsuarioDTO dto) {
        Usuario usuario = new Usuario();

        usuario.setNombre(dto.getNombre());
        usuario.setCorreo(dto.getCorreo());
        usuario.setPassword(passwordEncoder.encode(dto.getPassword()));

        usuario.setEsPrincipal(false);

        usuario.setTipoUsuario(
                tipoUsuarioRepository.findByNombre("USUARIO")
                        .orElseThrow(() -> new RuntimeException(
                                "No existe el tipo de usuario USUARIO"))
        );

        Usuario nuevoUsuario = usuarioRepository.save(usuario);

        log.info("Nuevo usuario guardado con ID: {}", nuevoUsuario.getId());

        return convertirADTO(nuevoUsuario);
    }

    @Transactional
    public UsuarioDTO actualizarUsuario(
            Integer id,
            UsuarioDTO dto,
            String correoActor) {

        Usuario actor = buscarActor(correoActor);

        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Usuario no encontrado con la ID: " + id));

        boolean actorPrincipal = actor.isEsPrincipal();
        boolean destinoPrincipal = usuarioExistente.isEsPrincipal();

        if (destinoPrincipal && !actorPrincipal) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "No puedes modificar la cuenta del administrador principal.");
        }

        TipoUsuario nuevoTipo = buscarTipoUsuario(dto.getTipoUsuarioId());

        boolean cambiaRol =
                !nuevoTipo.getId().equals(
                        usuarioExistente.getTipoUsuario().getId());

        if (cambiaRol && !actorPrincipal) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Solo el administrador principal puede cambiar los roles.");
        }

        if (destinoPrincipal && cambiaRol) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "No puedes cambiar el rol del administrador principal.");
        }

        usuarioExistente.setNombre(dto.getNombre());
        usuarioExistente.setCorreo(dto.getCorreo());

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            usuarioExistente.setPassword(
                    passwordEncoder.encode(dto.getPassword()));
        }

        usuarioExistente.setTipoUsuario(nuevoTipo);

        Usuario actualizado = usuarioRepository.save(usuarioExistente);

        log.info("Usuario con ID {} actualizado por {}", id, correoActor);

        return convertirADTO(actualizado);
    }

    @Transactional
    public void eliminarUsuario(Integer id, String correoActor) {

        Usuario actor = buscarActor(correoActor);

        Usuario destino = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Usuario no encontrado con la ID: " + id));

        if (destino.isEsPrincipal()) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "La cuenta del administrador principal está protegida.");
        }

        boolean destinoEsAdmin =
                destino.getTipoUsuario() != null
                && "ADMIN".equalsIgnoreCase(
                        destino.getTipoUsuario().getNombre());

        if (destinoEsAdmin && !actor.isEsPrincipal()) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Solo el administrador principal puede eliminar otros administradores.");
        }

        usuarioRepository.delete(destino);

        log.info("Usuario con ID {} eliminado por {}", id, correoActor);
    }

    private Usuario buscarActor(String correo) {
        Usuario actor = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.FORBIDDEN,
                        "No se pudo identificar al administrador."));

        if (!"ADMIN".equalsIgnoreCase(
                actor.getTipoUsuario().getNombre())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Se requiere una cuenta ADMIN.");
        }

        return actor;
    }

    private TipoUsuario buscarTipoUsuario(Integer id) {
        if (id == null) {
            throw new RuntimeException("El tipo de usuario es obligatorio");
        }

        return tipoUsuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Tipo de usuario no encontrado con la ID: " + id));
    }

    private UsuarioDTO convertirADTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();

        dto.setId(usuario.getId());
        dto.setNombre(usuario.getNombre());
        dto.setCorreo(usuario.getCorreo());
        dto.setTipoUsuarioId(
                usuario.getTipoUsuario() != null
                        ? usuario.getTipoUsuario().getId()
                        : null);

        dto.setEsPrincipal(usuario.isEsPrincipal());

        return dto;
    }
}

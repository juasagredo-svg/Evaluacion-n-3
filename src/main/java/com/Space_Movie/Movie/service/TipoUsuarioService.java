package com.Space_Movie.Movie.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.Space_Movie.Movie.DTO.TipoUsuarioDTO;
import com.Space_Movie.Movie.Model.TipoUsuario;
import com.Space_Movie.Movie.Repository.TipoUsuarioRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
@Slf4j
public class TipoUsuarioService {

    @Autowired
    private TipoUsuarioRepository tipoUsuarioRepository;


    public List<TipoUsuarioDTO> obtenerTiposUsuario() {
        log.info("Obteniendo todos los tipos de usuario");
        return tipoUsuarioRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .toList();
    }

    public TipoUsuarioDTO obtenerTipoUsuarioPorId(Integer id) {
        return tipoUsuarioRepository.findById(id)
                .map(this::convertirADTO)
                .orElseThrow(() -> new RuntimeException("Tipo de usuario no encontrado con la ID: " + id));
    }

    @Transactional
    public TipoUsuarioDTO guardarTipoUsuario(TipoUsuarioDTO dto) {
        TipoUsuario tipoUsuario = new TipoUsuario();
        tipoUsuario.setNombre(dto.getNombre());
        tipoUsuario.setTipoCuenta(dto.getTipoCuenta());

        TipoUsuario nuevoTipo = tipoUsuarioRepository.save(tipoUsuario);
        log.info("Nuevo tipo de usuario guardado exitosamente con ID: {}", nuevoTipo.getId());
        return convertirADTO(nuevoTipo);
    }

    @Transactional
    public TipoUsuarioDTO actualizarTipoUsuario(Integer id, TipoUsuarioDTO dto) {
        TipoUsuario tipoExistente = tipoUsuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo de usuario no encontrado con la ID: " + id));

        tipoExistente.setNombre(dto.getNombre());
        tipoExistente.setTipoCuenta(dto.getTipoCuenta());

        TipoUsuario tipoActualizado = tipoUsuarioRepository.save(tipoExistente);
        log.info("Tipo de usuario con ID: {} actualizado", id);
        return convertirADTO(tipoActualizado);
    }

    @Transactional
    public void eliminarTipoUsuario(Integer id) {
        if (!tipoUsuarioRepository.existsById(id)) {
            throw new RuntimeException("Tipo de usuario no encontrado con la ID: " + id);
        }
        tipoUsuarioRepository.deleteById(id);
        log.info("Tipo de usuario con ID: {} ha sido eliminado exitosamente", id);
    }

    private TipoUsuarioDTO convertirADTO(TipoUsuario tipoUsuario) {
        TipoUsuarioDTO dto = new TipoUsuarioDTO();
        dto.setId(tipoUsuario.getId());
        dto.setNombre(tipoUsuario.getNombre());
        dto.setTipoCuenta(tipoUsuario.getTipoCuenta());
        return dto;
    }
}

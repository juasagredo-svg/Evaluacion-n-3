package com.Space_Movie.Movie.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Space_Movie.Movie.DTO.ResenaDTO;
import com.Space_Movie.Movie.Model.ProductoAudiovisual;
import com.Space_Movie.Movie.Model.Reseña;
import com.Space_Movie.Movie.Model.TipoReseña;
import com.Space_Movie.Movie.Model.Usuario;
import com.Space_Movie.Movie.Model.Valoracion;
import com.Space_Movie.Movie.Repository.ProductoAudiovisualRepository;
import com.Space_Movie.Movie.Repository.ReseñaRepository;
import com.Space_Movie.Movie.Repository.TipoReseñaRepository;
import com.Space_Movie.Movie.Repository.UsuarioRepository;
import com.Space_Movie.Movie.Repository.ValoracionRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
@Slf4j
public class ReseñaService {

    @Autowired
    private ReseñaRepository reseñaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ProductoAudiovisualRepository productoAudiovisualRepository;
    @Autowired
    private TipoReseñaRepository tipoReseñaRepository;
    @Autowired
    private ValoracionRepository valoracionRepository;


    public List<ResenaDTO> obtenerReseñas() {
        log.info("Obteniendo todas las reseñas");
        return reseñaRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .toList();
    }

    public ResenaDTO obtenerReseñaPorId(Integer id) {
        return reseñaRepository.findById(id)
                .map(this::convertirADTO)
                .orElseThrow(() -> new RuntimeException("Reseña no encontrada con la ID: " + id));
    }

    @Transactional
    public ResenaDTO guardarReseña(ResenaDTO dto) {
        Reseña reseña = new Reseña();
        copiarDatos(reseña, dto);

        Reseña nuevaReseña = reseñaRepository.save(reseña);
        log.info("Nueva reseña guardada exitosamente con ID: {}", nuevaReseña.getId());
        return convertirADTO(nuevaReseña);
    }

    @Transactional
    public ResenaDTO actualizarReseña(Integer id, ResenaDTO dto) {
        Reseña reseñaExistente = reseñaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reseña no encontrada con la ID: " + id));

        copiarDatos(reseñaExistente, dto);

        Reseña reseñaActualizada = reseñaRepository.save(reseñaExistente);
        log.info("Reseña con ID: {} actualizada", id);
        return convertirADTO(reseñaActualizada);
    }

    @Transactional
    public void eliminarReseña(Integer id) {
        if (!reseñaRepository.existsById(id)) {
            throw new RuntimeException("Reseña no encontrada con la ID: " + id);
        }
        reseñaRepository.deleteById(id);
        log.info("Reseña con ID: {} ha sido eliminada exitosamente", id);
    }

    private void copiarDatos(Reseña reseña, ResenaDTO dto) {
        reseña.setContenido(dto.getContenido());
        reseña.setFechaCreacion(dto.getFechaCreacion() != null ? dto.getFechaCreacion() : LocalDateTime.now());
        reseña.setUsuario(buscarUsuario(dto.getUsuarioId()));
        reseña.setProducto(buscarProducto(dto.getProductoId()));
        reseña.setTipoResena(buscarTipoReseña(dto.getTipoResenaId()));
        reseña.setValoracion(buscarValoracion(dto.getValoracionId()));
    }

    private Usuario buscarUsuario(Integer id) {
        if (id == null) {
            throw new RuntimeException("El usuario es obligatorio");
        }
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con la ID: " + id));
    }

    private ProductoAudiovisual buscarProducto(Integer id) {
        if (id == null) {
            throw new RuntimeException("El producto audiovisual es obligatorio");
        }
        return productoAudiovisualRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto audiovisual no encontrado con la ID: " + id));
    }

    private TipoReseña buscarTipoReseña(Integer id) {
        if (id == null) {
            throw new RuntimeException("El tipo de reseña es obligatorio");
        }
        return tipoReseñaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo de reseña no encontrado con la ID: " + id));
    }

    private Valoracion buscarValoracion(Integer id) {
        if (id == null) {
            throw new RuntimeException("La valoración es obligatoria");
        }
        return valoracionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Valoración no encontrada con la ID: " + id));
    }

    private ResenaDTO convertirADTO(Reseña reseña) {
    ResenaDTO dto = new ResenaDTO();

    dto.setId(reseña.getId());
    dto.setContenido(reseña.getContenido());
    dto.setFechaCreacion(reseña.getFechaCreacion());

    dto.setUsuarioId(
        reseña.getUsuario() != null ? reseña.getUsuario().getId() : null
    );
    dto.setUsuarioNombre(
        reseña.getUsuario() != null ? reseña.getUsuario().getNombre() : null
    );
    dto.setUsuarioCorreo(
        reseña.getUsuario() != null ? reseña.getUsuario().getCorreo() : null
    );

    dto.setProductoId(
        reseña.getProducto() != null ? reseña.getProducto().getId() : null
    );
    dto.setProductoTitulo(
        reseña.getProducto() != null ? reseña.getProducto().getTitulo() : null
    );

    dto.setTipoResenaId(
        reseña.getTipoResena() != null ? reseña.getTipoResena().getId() : null
    );
    dto.setTipoResenaNombre(
        reseña.getTipoResena() != null ? reseña.getTipoResena().getNombre() : null
    );

    dto.setValoracionId(
        reseña.getValoracion() != null ? reseña.getValoracion().getId() : null
    );
    dto.setValoracionPuntuacion(
        reseña.getValoracion() != null ? reseña.getValoracion().getPuntuacion() : null
    );

    return dto;
    }
}

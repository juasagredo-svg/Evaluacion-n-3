package com.Space_Movie.Movie.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Space_Movie.Movie.DTO.TipoResenaDTO;
import com.Space_Movie.Movie.Model.TipoReseña;
import com.Space_Movie.Movie.Repository.TipoReseñaRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
@Slf4j
public class TipoReseñaService {

    @Autowired
    private TipoReseñaRepository tipoReseñaRepository;


    public List<TipoResenaDTO> obtenerTiposResena() {
        log.info("Obteniendo todos los tipos de reseña");
        return tipoReseñaRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .toList();
    }

    public TipoResenaDTO obtenerTipoResenaPorId(Integer id) {
        return tipoReseñaRepository.findById(id)
                .map(this::convertirADTO)
                .orElseThrow(() -> new RuntimeException("Tipo de reseña no encontrado con la ID: " + id));
    }

    @Transactional
    public TipoResenaDTO guardarTipoResena(TipoResenaDTO dto) {
        TipoReseña tipoResena = new TipoReseña();
        tipoResena.setNombre(dto.getNombre());

        TipoReseña nuevoTipo = tipoReseñaRepository.save(tipoResena);
        log.info("Nuevo tipo de reseña guardado exitosamente con ID: {}", nuevoTipo.getId());
        return convertirADTO(nuevoTipo);
    }

    @Transactional
    public TipoResenaDTO actualizarTipoResena(Integer id, TipoResenaDTO dto) {
        TipoReseña tipoExistente = tipoReseñaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo de reseña no encontrado con la ID: " + id));

        tipoExistente.setNombre(dto.getNombre());

        TipoReseña tipoActualizado = tipoReseñaRepository.save(tipoExistente);
        log.info("Tipo de reseña con ID: {} actualizado", id);
        return convertirADTO(tipoActualizado);
    }

    @Transactional
    public void eliminarTipoResena(Integer id) {
        if (!tipoReseñaRepository.existsById(id)) {
            throw new RuntimeException("Tipo de reseña no encontrado con la ID: " + id);
        }
        tipoReseñaRepository.deleteById(id);
        log.info("Tipo de reseña con ID: {} ha sido eliminada exitosamente", id);
    }

    private TipoResenaDTO convertirADTO(TipoReseña tipoResena) {
        TipoResenaDTO dto = new TipoResenaDTO();
        dto.setId(tipoResena.getId());
        dto.setNombre(tipoResena.getNombre());
        return dto;
    }
}

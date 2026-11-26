package com.Space_Movie.Movie.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Space_Movie.Movie.DTO.ValoracionDTO;
import com.Space_Movie.Movie.Model.Valoracion;
import com.Space_Movie.Movie.Repository.ValoracionRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
@Slf4j
public class ValoracionService {

    @Autowired
    private ValoracionRepository valoracionRepository;


    public List<ValoracionDTO> obtenerValoraciones() {
        log.info("Obteniendo todas las valoraciones");
        return valoracionRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .toList();
    }

    public ValoracionDTO obtenerValoracionPorId(Integer id) {
        return valoracionRepository.findById(id)
                .map(this::convertirADTO)
                .orElseThrow(() -> new RuntimeException("Valoración no encontrada con la ID: " + id));
    }

    @Transactional
    public ValoracionDTO guardarValoracion(ValoracionDTO dto) {
        Valoracion valoracion = new Valoracion();
        valoracion.setPuntuacion(dto.getPuntuacion());

        Valoracion nuevaValoracion = valoracionRepository.save(valoracion);
        log.info("Nueva valoración guardada exitosamente con ID: {}", nuevaValoracion.getId());
        return convertirADTO(nuevaValoracion);
    }

    @Transactional
    public ValoracionDTO actualizarValoracion(Integer id, ValoracionDTO dto) {
        Valoracion valoracionExistente = valoracionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Valoración no encontrada con la ID: " + id));

        valoracionExistente.setPuntuacion(dto.getPuntuacion());

        Valoracion valoracionActualizada = valoracionRepository.save(valoracionExistente);
        log.info("Valoración con ID: {} actualizada", id);
        return convertirADTO(valoracionActualizada);
    }

    @Transactional
    public void eliminarValoracion(Integer id) {
        if (!valoracionRepository.existsById(id)) {
            throw new RuntimeException("Valoración no encontrada con la ID: " + id);
        }
        valoracionRepository.deleteById(id);
        log.info("Valoración con ID: {} ha sido eliminada exitosamente", id);
    }

    private ValoracionDTO convertirADTO(Valoracion valoracion) {
        ValoracionDTO dto = new ValoracionDTO();
        dto.setId(valoracion.getId());
        dto.setPuntuacion(valoracion.getPuntuacion());
        return dto;
    }
}

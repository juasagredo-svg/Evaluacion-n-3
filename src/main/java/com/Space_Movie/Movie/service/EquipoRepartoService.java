package com.Space_Movie.Movie.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Space_Movie.Movie.DTO.EquipoRepartoDTO;
import com.Space_Movie.Movie.Model.EquipoReparto;
import com.Space_Movie.Movie.Repository.EquipoRepartoRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
@Slf4j
public class EquipoRepartoService {

    @Autowired
    private EquipoRepartoRepository equipoRepartoRepository;


    public List<EquipoRepartoDTO> obtenerEquipoReparto() {
        log.info("Obteniendo todo el equipo de reparto");
        return equipoRepartoRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .toList();
    }

    public EquipoRepartoDTO obtenerEquipoRepartoPorId(Integer id) {
        return equipoRepartoRepository.findById(id)
                .map(this::convertirADTO)
                .orElseThrow(() -> new RuntimeException("Equipo de reparto no encontrado con la ID: " + id));
    }

    @Transactional
    public EquipoRepartoDTO guardarEquipoReparto(EquipoRepartoDTO dto) {
        EquipoReparto equipo = new EquipoReparto();
        equipo.setNombre(dto.getNombre());
        equipo.setApellido(dto.getApellido());
        equipo.setEdad(dto.getEdad());
        equipo.setPersonaje(dto.getPersonaje());

        EquipoReparto nuevoEquipo = equipoRepartoRepository.save(equipo);
        log.info("Nuevo equipo de reparto guardado exitosamente con ID: {}", nuevoEquipo.getId());
        return convertirADTO(nuevoEquipo);
    }

    @Transactional
    public EquipoRepartoDTO actualizarEquipoReparto(Integer id, EquipoRepartoDTO dto) {
        EquipoReparto equipoExistente = equipoRepartoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipo de reparto no encontrado con la ID: " + id));

        equipoExistente.setNombre(dto.getNombre());
        equipoExistente.setApellido(dto.getApellido());
        equipoExistente.setEdad(dto.getEdad());
        equipoExistente.setPersonaje(dto.getPersonaje());

        EquipoReparto equipoActualizado = equipoRepartoRepository.save(equipoExistente);
        log.info("Equipo de reparto con ID: {} actualizado", id);
        return convertirADTO(equipoActualizado);
    }

    @Transactional
    public void eliminarEquipoReparto(Integer id) {
        if (!equipoRepartoRepository.existsById(id)) {
            throw new RuntimeException("Equipo de reparto no encontrado con la ID: " + id);
        }
        equipoRepartoRepository.deleteById(id);
        log.info("Equipo de reparto con ID: {} ha sido eliminado exitosamente", id);
    }

    private EquipoRepartoDTO convertirADTO(EquipoReparto equipo) {
        EquipoRepartoDTO dto = new EquipoRepartoDTO();
        dto.setId(equipo.getId());
        dto.setNombre(equipo.getNombre());
        dto.setApellido(equipo.getApellido());
        dto.setEdad(equipo.getEdad());
        dto.setPersonaje(equipo.getPersonaje());
        return dto;
    }
}

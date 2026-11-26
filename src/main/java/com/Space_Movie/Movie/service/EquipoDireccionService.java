package com.Space_Movie.Movie.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Space_Movie.Movie.DTO.EquipoDireccionDTO;
import com.Space_Movie.Movie.Model.EquipoDireccion;
import com.Space_Movie.Movie.Repository.EquipoDireccionRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
@Slf4j
public class EquipoDireccionService {

    @Autowired
    private EquipoDireccionRepository equipoDireccionRepository;


    public List<EquipoDireccionDTO> obtenerEquipoDireccion() {
        log.info("Obteniendo todo el equipo de dirección");
        return equipoDireccionRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .toList();
    }

    public EquipoDireccionDTO obtenerEquipoDireccionPorId(Integer id) {
        return equipoDireccionRepository.findById(id)
                .map(this::convertirADTO)
                .orElseThrow(() -> new RuntimeException("Equipo de dirección no encontrado con la ID: " + id));
    }

    @Transactional
    public EquipoDireccionDTO guardarEquipoDireccion(EquipoDireccionDTO dto) {
        EquipoDireccion equipo = new EquipoDireccion();
        equipo.setNombre(dto.getNombre());
        equipo.setApellido(dto.getApellido());
        equipo.setEdad(dto.getEdad());
        equipo.setCargo(dto.getCargo());

        EquipoDireccion nuevoEquipo = equipoDireccionRepository.save(equipo);
        log.info("Nuevo equipo de dirección guardado exitosamente con ID: {}", nuevoEquipo.getId());
        return convertirADTO(nuevoEquipo);
    }

    @Transactional
    public EquipoDireccionDTO actualizarEquipoDireccion(Integer id, EquipoDireccionDTO dto) {
        EquipoDireccion equipoExistente = equipoDireccionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipo de dirección no encontrado con la ID: " + id));

        equipoExistente.setNombre(dto.getNombre());
        equipoExistente.setApellido(dto.getApellido());
        equipoExistente.setEdad(dto.getEdad());
        equipoExistente.setCargo(dto.getCargo());

        EquipoDireccion equipoActualizado = equipoDireccionRepository.save(equipoExistente);
        log.info("Equipo de dirección con ID: {} actualizado", id);
        return convertirADTO(equipoActualizado);
    }

    @Transactional
    public void eliminarEquipoDireccion(Integer id) {
        if (!equipoDireccionRepository.existsById(id)) {
            throw new RuntimeException("Equipo de dirección no encontrado con la ID: " + id);
        }
        equipoDireccionRepository.deleteById(id);
        log.info("Equipo de dirección con ID: {} ha sido eliminado exitosamente", id);
    }

    private EquipoDireccionDTO convertirADTO(EquipoDireccion equipo) {
        EquipoDireccionDTO dto = new EquipoDireccionDTO();
        dto.setId(equipo.getId());
        dto.setNombre(equipo.getNombre());
        dto.setApellido(equipo.getApellido());
        dto.setEdad(equipo.getEdad());
        dto.setCargo(equipo.getCargo());
        return dto;
    }
}

package com.Space_Movie.Movie.controller;
import com.Space_Movie.Movie.DTO.EquipoDireccionDTO;
import com.Space_Movie.Movie.service.EquipoDireccionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/equipos-direccion")
@Slf4j
public class EquipoDireccionController {

    @Autowired
    private EquipoDireccionService equipoDireccionService;

    // guardar
    @PostMapping
    public ResponseEntity<EquipoDireccionDTO> crear(@Valid @RequestBody EquipoDireccionDTO dto) {
        EquipoDireccionDTO nuevo = equipoDireccionService.guardarEquipoDireccion(dto);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    // listar
    @GetMapping
    public ResponseEntity<List<EquipoDireccionDTO>> listar() {
        log.info("Consultando lista completa");
        List<EquipoDireccionDTO> lista = equipoDireccionService.obtenerEquipoDireccion();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    // buscar por id
    @GetMapping("/{id}")
    public ResponseEntity<EquipoDireccionDTO> obtener(@PathVariable Integer id) {
        EquipoDireccionDTO dto = equipoDireccionService.obtenerEquipoDireccionPorId(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    // eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        equipoDireccionService.eliminarEquipoDireccion(id);
        return ResponseEntity.ok("El registro con el Id: " + id + " fue eliminado con exito");
    }

    @PutMapping("/{id}")
    public ResponseEntity<EquipoDireccionDTO> actualizar(@PathVariable Integer id, @Valid @RequestBody EquipoDireccionDTO dto) {
        EquipoDireccionDTO nuevo = equipoDireccionService.actualizarEquipoDireccion(id, dto);
        return ResponseEntity.ok(nuevo);
    }
}
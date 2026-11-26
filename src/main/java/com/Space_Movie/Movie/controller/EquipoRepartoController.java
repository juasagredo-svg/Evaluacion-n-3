package com.Space_Movie.Movie.controller;
import com.Space_Movie.Movie.DTO.EquipoRepartoDTO;
import com.Space_Movie.Movie.service.EquipoRepartoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/equipos-reparto")
@Slf4j
public class EquipoRepartoController {

    @Autowired
    private EquipoRepartoService equipoRepartoService;

    // guardar
    @PostMapping
    public ResponseEntity<EquipoRepartoDTO> crear(@Valid @RequestBody EquipoRepartoDTO dto) {
        EquipoRepartoDTO nuevo = equipoRepartoService.guardarEquipoReparto(dto);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    // listar
    @GetMapping
    public ResponseEntity<List<EquipoRepartoDTO>> listar() {
        log.info("Consultando lista completa");
        List<EquipoRepartoDTO> lista = equipoRepartoService.obtenerEquipoReparto();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    // buscar por id
    @GetMapping("/{id}")
    public ResponseEntity<EquipoRepartoDTO> obtener(@PathVariable Integer id) {
        EquipoRepartoDTO dto = equipoRepartoService.obtenerEquipoRepartoPorId(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    // eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        equipoRepartoService.eliminarEquipoReparto(id);
        return ResponseEntity.ok("El registro con el Id: " + id + " fue eliminado con exito");
    }

    @PutMapping("/{id}")
    public ResponseEntity<EquipoRepartoDTO> actualizar(@PathVariable Integer id, @Valid @RequestBody EquipoRepartoDTO dto) {
        EquipoRepartoDTO nuevo = equipoRepartoService.actualizarEquipoReparto(id, dto);
        return ResponseEntity.ok(nuevo);
    }
}
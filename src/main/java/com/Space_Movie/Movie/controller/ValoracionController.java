package com.Space_Movie.Movie.controller;
import com.Space_Movie.Movie.DTO.ValoracionDTO;
import com.Space_Movie.Movie.service.ValoracionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/valoraciones")
@Slf4j
public class ValoracionController {

    @Autowired
    private ValoracionService valoracionService;

    // guardar
    @PostMapping
    public ResponseEntity<ValoracionDTO> crear(@Valid @RequestBody ValoracionDTO dto) {
        ValoracionDTO nuevo = valoracionService.guardarValoracion(dto);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    // listar
    @GetMapping
    public ResponseEntity<List<ValoracionDTO>> listar() {
        log.info("Consultando lista completa");
        List<ValoracionDTO> lista = valoracionService.obtenerValoraciones();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    // buscar por id
    @GetMapping("/{id}")
    public ResponseEntity<ValoracionDTO> obtener(@PathVariable Integer id) {
        ValoracionDTO dto = valoracionService.obtenerValoracionPorId(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    // eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        valoracionService.eliminarValoracion(id);
        return ResponseEntity.ok("El registro con el Id: " + id + " fue eliminado con exito");
    }

    @PutMapping("/{id}")
    public ResponseEntity<ValoracionDTO> actualizar(@PathVariable Integer id, @Valid @RequestBody ValoracionDTO dto) {
        ValoracionDTO nuevo = valoracionService.actualizarValoracion(id, dto);
        return ResponseEntity.ok(nuevo);
    }
}
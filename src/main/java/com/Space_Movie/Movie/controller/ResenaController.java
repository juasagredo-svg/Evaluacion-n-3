package com.Space_Movie.Movie.controller;
import com.Space_Movie.Movie.DTO.ResenaDTO;
import com.Space_Movie.Movie.service.ReseñaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/resenas")
@Slf4j
public class ResenaController {

    @Autowired
    private ReseñaService reseñaService;

    // guardar
    @PostMapping
    public ResponseEntity<ResenaDTO> crear(@Valid @RequestBody ResenaDTO dto) {
        ResenaDTO nuevo = reseñaService.guardarReseña(dto);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    // listar
    @GetMapping
    public ResponseEntity<List<ResenaDTO>> listar() {
        log.info("Consultando lista completa");
        List<ResenaDTO> lista = reseñaService.obtenerReseñas();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    // buscar por id
    @GetMapping("/{id}")
    public ResponseEntity<ResenaDTO> obtener(@PathVariable Integer id) {
        ResenaDTO dto = reseñaService.obtenerReseñaPorId(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    // eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        reseñaService.eliminarReseña(id);
        return ResponseEntity.ok("El registro con el Id: " + id + " fue eliminado con exito");
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResenaDTO> actualizar(@PathVariable Integer id, @Valid @RequestBody ResenaDTO dto) {
        ResenaDTO nuevo = reseñaService.actualizarReseña(id, dto);
        return ResponseEntity.ok(nuevo);
    }
}

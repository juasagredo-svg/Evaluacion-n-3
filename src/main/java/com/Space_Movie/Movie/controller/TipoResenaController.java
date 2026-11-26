package com.Space_Movie.Movie.controller;
import com.Space_Movie.Movie.DTO.TipoResenaDTO;
import com.Space_Movie.Movie.service.TipoReseñaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tipos-resena")
@Slf4j
public class TipoResenaController {

    @Autowired
    private TipoReseñaService tipoResenaService;

    // guardar
    @PostMapping
    public ResponseEntity<TipoResenaDTO> crear(@Valid @RequestBody TipoResenaDTO dto) {
        TipoResenaDTO nuevo = tipoResenaService.guardarTipoResena(dto);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    // listar
    @GetMapping
    public ResponseEntity<List<TipoResenaDTO>> listar() {
        log.info("Consultando lista completa");
        List<TipoResenaDTO> lista = tipoResenaService.obtenerTiposResena();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    // buscar por id
    @GetMapping("/{id}")
    public ResponseEntity<TipoResenaDTO> obtener(@PathVariable Integer id) {
        TipoResenaDTO dto = tipoResenaService.obtenerTipoResenaPorId(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    // eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        tipoResenaService.eliminarTipoResena(id);
        return ResponseEntity.ok("El registro con el Id: " + id + " fue eliminado con exito");
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoResenaDTO> actualizar(@PathVariable Integer id, @Valid @RequestBody TipoResenaDTO dto) {
        TipoResenaDTO nuevo = tipoResenaService.actualizarTipoResena(id, dto);
        return ResponseEntity.ok(nuevo);
    }
}
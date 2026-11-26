package com.Space_Movie.Movie.controller;
import com.Space_Movie.Movie.DTO.TipoCategoriaDTO;
import com.Space_Movie.Movie.service.TipoCategoriaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tipos-categoria")
@Slf4j
public class TipoCategoriaController {

    @Autowired
    private TipoCategoriaService tipoCategoriaService;

    // guardar
    @PostMapping
    public ResponseEntity<TipoCategoriaDTO> crear(@Valid @RequestBody TipoCategoriaDTO dto) {
        TipoCategoriaDTO nuevo = tipoCategoriaService.guardarTipoCategoria(dto);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    // listar
    @GetMapping
    public ResponseEntity<List<TipoCategoriaDTO>> listar() {
        log.info("Consultando lista completa");
        List<TipoCategoriaDTO> lista = tipoCategoriaService.obtenerTiposCategoria();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    // buscar por id
    @GetMapping("/{id}")
    public ResponseEntity<TipoCategoriaDTO> obtener(@PathVariable Integer id) {
        TipoCategoriaDTO dto = tipoCategoriaService.obtenerTipoCategoriaPorId(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    // eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        tipoCategoriaService.eliminarTipoCategoria(id);
        return ResponseEntity.ok("El registro con el Id: " + id + " fue eliminado con exito");
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoCategoriaDTO> actualizar(@PathVariable Integer id, @Valid @RequestBody TipoCategoriaDTO dto) {
        TipoCategoriaDTO nuevo = tipoCategoriaService.actualizarTipoCategoria(id, dto);
        return ResponseEntity.ok(nuevo);
    }
}
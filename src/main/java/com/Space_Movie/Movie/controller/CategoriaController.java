package com.Space_Movie.Movie.controller;
import com.Space_Movie.Movie.DTO.CategoriaDTO;
import com.Space_Movie.Movie.service.CategoriaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/categorias")
@Slf4j
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    // guardar
    @PostMapping
    public ResponseEntity<CategoriaDTO> crear(@Valid @RequestBody CategoriaDTO dto) {
        CategoriaDTO nuevo = categoriaService.guardarCategoria(dto);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    // listar
    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> listar() {
        log.info("Consultando lista completa");
        List<CategoriaDTO> lista = categoriaService.obtenerCategoria();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    // buscar por id
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> obtener(@PathVariable Integer id) {
        CategoriaDTO dto = categoriaService.obtenerCategoriaPorId(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    // eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        categoriaService.eliminarCategoria(id);
        return ResponseEntity.ok("El registro con el Id: " + id + " fue eliminado con exito");
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaDTO> actualizar(@PathVariable Integer id, @Valid @RequestBody CategoriaDTO dto) {
        CategoriaDTO nuevo = categoriaService.actualizarCategoria(id, dto);
        return ResponseEntity.ok(nuevo);
    }
}
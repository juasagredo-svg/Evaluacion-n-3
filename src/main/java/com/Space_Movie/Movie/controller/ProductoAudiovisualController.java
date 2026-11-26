package com.Space_Movie.Movie.controller;
import com.Space_Movie.Movie.DTO.ProductoAudiovisualDTO;
import com.Space_Movie.Movie.service.ProductoAudiovisualService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/productos")
@Slf4j
public class ProductoAudiovisualController {

    @Autowired
    private ProductoAudiovisualService productoAudiovisualService;

    // guardar
    @PostMapping
    public ResponseEntity<ProductoAudiovisualDTO> crear(@Valid @RequestBody ProductoAudiovisualDTO dto) {
        ProductoAudiovisualDTO nuevo = productoAudiovisualService.guardarProducto(dto);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    // listar
    @GetMapping
    public ResponseEntity<List<ProductoAudiovisualDTO>> listar() {
        log.info("Consultando lista completa");
        List<ProductoAudiovisualDTO> lista = productoAudiovisualService.obtenerProductos();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    // buscar por id
    @GetMapping("/{id}")
    public ResponseEntity<ProductoAudiovisualDTO> obtener(@PathVariable Integer id) {
        ProductoAudiovisualDTO dto = productoAudiovisualService.obtenerProductoPorId(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    // eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        productoAudiovisualService.eliminarProducto(id);
        return ResponseEntity.ok("El registro con el Id: " + id + " fue eliminado con exito");
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoAudiovisualDTO> actualizar(@PathVariable Integer id, @Valid @RequestBody ProductoAudiovisualDTO dto) {
        ProductoAudiovisualDTO nuevo = productoAudiovisualService.actualizarProducto(id, dto);
        return ResponseEntity.ok(nuevo);
    }
}
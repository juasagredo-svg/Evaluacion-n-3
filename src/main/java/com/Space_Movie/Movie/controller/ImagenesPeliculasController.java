package com.Space_Movie.Movie.controller;
import com.Space_Movie.Movie.DTO.ImagenPeliculaDTO;
import com.Space_Movie.Movie.service.ImagenesPeliculasService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/imagenes")
@Slf4j
public class ImagenesPeliculasController {

    @Autowired
    private ImagenesPeliculasService imagenesPeliculasService;

    // guardar
    @PostMapping
    public ResponseEntity<ImagenPeliculaDTO> crear(@Valid @RequestBody ImagenPeliculaDTO dto) {
        ImagenPeliculaDTO nuevo = imagenesPeliculasService.guardarImagen(dto);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    // listar
    @GetMapping
    public ResponseEntity<List<ImagenPeliculaDTO>> listar() {
        log.info("Consultando lista completa");
        List<ImagenPeliculaDTO> lista = imagenesPeliculasService.obtenerImagenes();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    // buscar por id
    @GetMapping("/{id}")
    public ResponseEntity<ImagenPeliculaDTO> obtener(@PathVariable Integer id) {
        ImagenPeliculaDTO dto = imagenesPeliculasService.obtenerImagenPorId(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    // eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        imagenesPeliculasService.eliminarImagen(id);
        return ResponseEntity.ok("El registro con el Id: " + id + " fue eliminado con exito");
    }

    @PutMapping("/{id}")
    public ResponseEntity<ImagenPeliculaDTO> actualizar(@PathVariable Integer id, @Valid @RequestBody ImagenPeliculaDTO dto) {
        ImagenPeliculaDTO nuevo = imagenesPeliculasService.actualizarImagen(id, dto);
        return ResponseEntity.ok(nuevo);
    }
}
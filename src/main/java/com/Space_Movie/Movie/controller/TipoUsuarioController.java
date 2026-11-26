package com.Space_Movie.Movie.controller;
import com.Space_Movie.Movie.DTO.TipoUsuarioDTO;
import com.Space_Movie.Movie.service.TipoUsuarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tipos-usuario")
@Slf4j
public class TipoUsuarioController {

    @Autowired
    private TipoUsuarioService tipoUsuarioService;

    // guardar
    @PostMapping
    public ResponseEntity<TipoUsuarioDTO> crear(@Valid @RequestBody TipoUsuarioDTO dto) {
        TipoUsuarioDTO nuevo = tipoUsuarioService.guardarTipoUsuario(dto);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    // listar
    @GetMapping
    public ResponseEntity<List<TipoUsuarioDTO>> listar() {
        log.info("Consultando lista completa");
        List<TipoUsuarioDTO> lista = tipoUsuarioService.obtenerTiposUsuario();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    // buscar por id
    @GetMapping("/{id}")
    public ResponseEntity<TipoUsuarioDTO> obtener(@PathVariable Integer id) {
        TipoUsuarioDTO dto = tipoUsuarioService.obtenerTipoUsuarioPorId(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    // eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        tipoUsuarioService.eliminarTipoUsuario(id);
        return ResponseEntity.ok("El registro con el Id: " + id + " fue eliminado con exito");
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoUsuarioDTO> actualizar(@PathVariable Integer id, @Valid @RequestBody TipoUsuarioDTO dto) {
        TipoUsuarioDTO nuevo = tipoUsuarioService.actualizarTipoUsuario(id, dto);
        return ResponseEntity.ok(nuevo);
    }
}
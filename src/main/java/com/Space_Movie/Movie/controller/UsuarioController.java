package com.Space_Movie.Movie.controller;
import com.Space_Movie.Movie.DTO.UsuarioDTO;
import com.Space_Movie.Movie.service.UsuarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios")
@Slf4j
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // guardar
    @PostMapping
    public ResponseEntity<UsuarioDTO> crear(@Valid @RequestBody UsuarioDTO dto) {
        UsuarioDTO nuevo = usuarioService.guardarUsuario(dto);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    // listar
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listar() {
        log.info("Consultando lista completa");
        List<UsuarioDTO> lista = usuarioService.obtenerUsuarios();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    // buscar por id
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> obtener(@PathVariable Integer id) {
        UsuarioDTO dto = usuarioService.obtenerUsuarioPorId(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    // Eliminar usuario
@DeleteMapping("/{id}")
public ResponseEntity<String> eliminar(
        @PathVariable Integer id,
        Authentication authentication) {

    usuarioService.eliminarUsuario(
            id,
            authentication.getName()
    );

    return ResponseEntity.ok(
            "El registro con el Id: " + id + " fue eliminado con éxito"
    );
}

// Actualizar usuario
@PutMapping("/{id}")
public ResponseEntity<UsuarioDTO> actualizar(
        @PathVariable Integer id,
        @Valid @RequestBody UsuarioDTO dto,
        Authentication authentication) {

    UsuarioDTO nuevo = usuarioService.actualizarUsuario(
            id,
            dto,
            authentication.getName()
    );

    return ResponseEntity.ok(nuevo);
}
}
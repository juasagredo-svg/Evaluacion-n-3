package com.Space_Movie.Movie.Repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.Space_Movie.Movie.Model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Integer > {

    Optional<Usuario> findByCorreo(String correo);
    
    Optional<Usuario> findByEsPrincipalTrue();
}

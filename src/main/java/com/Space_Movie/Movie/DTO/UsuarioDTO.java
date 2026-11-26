package com.Space_Movie.Movie.DTO;

import lombok.Data;

@Data
public class UsuarioDTO {

    private Integer id;
    private String nombre;
    private String correo;
    private String password;
    private Integer tipoUsuarioId;
    private Boolean esPrincipal;
}

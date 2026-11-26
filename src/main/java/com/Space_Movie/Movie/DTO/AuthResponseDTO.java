package com.Space_Movie.Movie.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponseDTO {

    private String token;

    private String correo;

    private String rol;
}

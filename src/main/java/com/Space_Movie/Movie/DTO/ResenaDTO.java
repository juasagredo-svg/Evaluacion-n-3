
package com.Space_Movie.Movie.DTO;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ResenaDTO {

    private Integer id;
    private String contenido;
    private LocalDateTime fechaCreacion;

    private Integer usuarioId;
    private String usuarioNombre;
    private String usuarioCorreo;

    private Integer productoId;
    private String productoTitulo;

    private Integer tipoResenaId;
    private String tipoResenaNombre;

    private Integer valoracionId;
    private Integer valoracionPuntuacion;
}

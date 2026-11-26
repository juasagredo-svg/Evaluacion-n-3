package com.Space_Movie.Movie.DTO;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ProductoAudiovisualDTO {

    private Integer id;
    private String poster;
    private String titulo;
    private String sinopsis;
    private Integer anio;
    private Integer duracionMinutos;
    private LocalDate fechaEstreno;
    private String genero;
    private Integer aprobacionPublico;
    private Integer aprobacionCritica;
    private Integer equipoDireccionId;
    private Integer categoriaId;
}

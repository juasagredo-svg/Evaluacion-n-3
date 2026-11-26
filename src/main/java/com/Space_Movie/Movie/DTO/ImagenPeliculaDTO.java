package com.Space_Movie.Movie.DTO;

import lombok.Data;

@Data
public class ImagenPeliculaDTO {

    private Integer id;
    private String url;
    private Boolean esPrincipal;
    private Integer productoAudiovisualId;
}

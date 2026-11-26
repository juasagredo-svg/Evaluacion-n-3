package com.Space_Movie.Movie.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
@Table(name = "Imagen_Pelicula")
public class ImagenPelicula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "La URL no puede estar vacía")
    @Size(max = 500, message = "La URL no puede superar los 500 caracteres")
    @Column(name = "url", nullable = false, length = 500)
    private String url;

    @NotNull(message = "Debe indicar si es la imagen principal o no")
    @Column(name = "es_principal", nullable = false)
    private Boolean esPrincipal; // Usamos Boolean en vez de boolean primitivo para el @NotNull

    @NotNull(message = "El producto audiovisual no puede ser nulo")
    @ManyToOne
    @JoinColumn(name = "producto_audiovisual_id", nullable = false)
    private ProductoAudiovisual productoAudiovisual;
}
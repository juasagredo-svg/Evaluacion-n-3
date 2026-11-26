package com.Space_Movie.Movie.Model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
@Table(name = "Resena")
public class Reseña {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El contenido de la reseña no puede estar vacío")
    @Size(min = 10, max = 3000, message = "La reseña debe tener entre 10 y 3000 caracteres")
    @Column(name = "contenido", nullable = false, length = 3000)
    private String contenido;

    @NotNull(message = "La fecha de creación no puede ser nula")
    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @NotNull(message = "El usuario no puede ser nulo")
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @NotNull(message = "El producto audiovisual no puede ser nulo")
    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private ProductoAudiovisual producto;

    @NotNull(message = "El tipo de reseña no puede ser nulo")
    @ManyToOne
    @JoinColumn(name = "tipo_resena_id", nullable = false)
    private TipoReseña tipoResena;

    @NotNull(message = "La valoración no puede ser nula")
    @OneToOne
    @JoinColumn(name = "valoracion_id", nullable = false)
    private Valoracion valoracion;
}
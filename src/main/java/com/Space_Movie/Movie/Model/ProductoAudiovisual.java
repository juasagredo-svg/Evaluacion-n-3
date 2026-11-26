package com.Space_Movie.Movie.Model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
@Table(name = "Producto_Audiovisual")
public class ProductoAudiovisual {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El poster no debe quedar vacío")
    @Size(max = 500, message = "La URL del poster no puede superar los 500 caracteres")
    @Column(name = "poster", nullable = false, length = 500)
    private String poster;

    @NotBlank(message = "El titulo no debe quedar vacío")
    @Size(min = 1, max = 150, message = "El título debe tener entre 1 y 150 caracteres")
    @Column(name = "titulo", nullable = false, length = 150)
    private String titulo;

    @NotBlank(message = "La sinopsis no debe quedar vacía")
    @Size(min = 10, max = 2000, message = "La sinopsis debe tener entre 10 y 2000 caracteres")
    @Column(name = "sinopsis", nullable = false, length = 2000)
    private String sinopsis;

    @NotNull(message = "El año no debe quedar vacío")
    @Min(value = 1888, message = "El año no puede ser menor a 1888")
    @Max(value = 2100, message = "El año no puede ser mayor a 2100")
    @Column(name = "anio", nullable = false)
    private Integer anio;

    @Min(value = 1, message = "La duración debe ser mayor a 0 minutos")
    @Max(value = 1000, message = "La duración no puede superar los 1000 minutos")
    @Column(name = "duracion_minutos")
    private Integer duracionMinutos;

    @NotNull(message = "La fecha de estreno no debe quedar vacía")
    @Column(name = "fecha_estreno", nullable = false)
    private LocalDate fechaEstreno;

    @Size(max = 100, message = "El género no puede superar los 100 caracteres")
    @Column(name = "genero", length = 100)
    private String genero;

    @Min(value = 0, message = "La aprobación del público no puede ser negativa")
    @Max(value = 100, message = "La aprobación del público no puede superar 100")
    @Column(name = "aprobacion_publico")
    private Integer aprobacionPublico;

    @Min(value = 0, message = "La aprobación de la crítica no puede ser negativa")
    @Max(value = 100, message = "La aprobación de la crítica no puede superar 100")
    @Column(name = "aprobacion_critica")
    private Integer aprobacionCritica;

    @NotNull(message = "El equipo de dirección no puede ser nulo")
    @ManyToOne
    @JoinColumn(name = "equipo_direccion_id", nullable = false)
    private EquipoDireccion equipoDireccion;

    @NotNull(message = "La categoría no puede ser nula")
    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;
}
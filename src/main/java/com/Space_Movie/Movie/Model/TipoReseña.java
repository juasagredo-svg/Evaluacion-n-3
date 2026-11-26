package com.Space_Movie.Movie.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
@Table(name = "Tipo_Resena")
public class TipoReseña {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El tipo de reseña no debe quedar vacío")
    @Size(min = 3, max = 50, message = "El tipo de reseña debe tener entre 3 y 50 caracteres")
    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;
}
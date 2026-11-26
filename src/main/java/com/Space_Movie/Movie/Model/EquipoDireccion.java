package com.Space_Movie.Movie.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
@Table(name = "Equipo_Direccion")
public class EquipoDireccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    @NotBlank(message = "El apellido no puede estar vacío")
    @Size(min = 2, max = 50, message = "El apellido debe tener entre 2 y 50 caracteres")
    @Column(name = "apellido", nullable = false, length = 50)
    private String apellido;

    @NotNull(message = "La edad no puede ser nula")
    @Min(value = 18, message = "La edad debe ser mayor o igual a 18 años")
    @Max(value = 100, message = "La edad no puede superar los 100 años")
    @Column(name = "edad", nullable = false)
    private Integer edad;

    @NotBlank(message = "El cargo no puede estar vacío")
    @Size(min = 3, max = 50, message = "El cargo debe tener entre 3 y 50 caracteres")
    @Column(name = "cargo", nullable = false, length = 50)
    private String cargo;
}
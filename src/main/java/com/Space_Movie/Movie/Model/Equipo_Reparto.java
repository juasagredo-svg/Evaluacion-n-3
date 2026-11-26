package com.Space_Movie.Movie.Model;




import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Data
@Table(name="Equipo_Reparto")
public class Equipo_Reparto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "Nombre" ,nullable = false)
    private String Nombre;

    @Column(name = "Apellido" ,nullable = false)
    private String Apellido;

    @Column(name = "Edad" ,nullable = false)
    private int Edad;

    @Column(name = "Personaje_Intepretado" ,nullable = false)
    private String Personaje_Intepretado;
}

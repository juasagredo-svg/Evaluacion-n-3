package com.Space_Movie.Movie.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Space_Movie.Movie.Model.Imagenes_Peliculas;

@Repository
public interface Imagenes_PeliculasRepository extends JpaRepository<Imagenes_Peliculas, Integer> {

}
package com.Space_Movie.Movie.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Space_Movie.Movie.Model.Equipo_Direccion;

@Repository
public interface Equipo_DireccionRepository extends JpaRepository<Equipo_Direccion, Integer> {

}
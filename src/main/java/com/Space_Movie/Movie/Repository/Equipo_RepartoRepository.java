package com.Space_Movie.Movie.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Space_Movie.Movie.Model.Equipo_Reparto;

@Repository
public interface Equipo_RepartoRepository extends JpaRepository<Equipo_Reparto, Integer> {

}
package com.Space_Movie.Movie.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Space_Movie.Movie.Model.EquipoReparto;

@Repository
public interface EquipoRepartoRepository extends JpaRepository<EquipoReparto, Integer> {

}
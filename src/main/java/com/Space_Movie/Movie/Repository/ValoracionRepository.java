package com.Space_Movie.Movie.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Space_Movie.Movie.Model.Valoracion;

@Repository
public interface ValoracionRepository extends JpaRepository<Valoracion, Integer> {

}
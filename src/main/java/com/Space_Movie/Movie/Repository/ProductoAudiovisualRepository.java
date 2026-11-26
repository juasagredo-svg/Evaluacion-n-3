package com.Space_Movie.Movie.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Space_Movie.Movie.Model.ProductoAudiovisual;

@Repository
public interface ProductoAudiovisualRepository extends JpaRepository<ProductoAudiovisual, Integer> {
    
}
package com.Space_Movie.Movie.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Space_Movie.Movie.DTO.CategoriaDTO;
import com.Space_Movie.Movie.Model.Categoria;
import com.Space_Movie.Movie.Repository.CategoriaRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
@Slf4j  
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;


    public List<CategoriaDTO> obtenerCategoria() {
        log.info("Obteniendo todas las categorías");
        return categoriaRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .toList();
    }

    public CategoriaDTO obtenerCategoriaPorId(Integer id) {
        return categoriaRepository.findById(id)
                .map(this::convertirADTO)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con la ID: " + id));
    }

    @Transactional
    public CategoriaDTO guardarCategoria(CategoriaDTO dto) {
        Categoria categoria = new Categoria();
        categoria.setNombre(dto.getNombre());

        Categoria nuevaCategoria = categoriaRepository.save(categoria);
        log.info("Nueva categoría guardada exitosamente con ID: {}", nuevaCategoria.getId());
        return convertirADTO(nuevaCategoria);
    }

    @Transactional
    public CategoriaDTO actualizarCategoria(Integer id, CategoriaDTO dto) {
        Categoria categoriaExistente = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con la ID: " + id));

        categoriaExistente.setNombre(dto.getNombre());

        Categoria categoriaActualizada = categoriaRepository.save(categoriaExistente);
        log.info("Categoría con ID: {} actualizada", id);
        return convertirADTO(categoriaActualizada);
    }

    @Transactional
    public void eliminarCategoria(Integer id) {
        if (!categoriaRepository.existsById(id)) {
            throw new RuntimeException("Categoría no encontrada con la ID: " + id);
        }
        categoriaRepository.deleteById(id);
        log.info("Categoría con ID: {} ha sido eliminada exitosamente.", id);
    }

    private CategoriaDTO convertirADTO(Categoria categoria) {
        CategoriaDTO dto = new CategoriaDTO();
        dto.setId(categoria.getId());
        dto.setNombre(categoria.getNombre());
        return dto;
    }
}



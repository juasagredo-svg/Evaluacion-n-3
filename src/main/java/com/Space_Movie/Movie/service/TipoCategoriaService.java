package com.Space_Movie.Movie.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Space_Movie.Movie.DTO.TipoCategoriaDTO;
import com.Space_Movie.Movie.Model.Categoria;
import com.Space_Movie.Movie.Model.TipoCategoria;
import com.Space_Movie.Movie.Repository.CategoriaRepository;
import com.Space_Movie.Movie.Repository.TipoCategoriaRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
@Slf4j
public class TipoCategoriaService {

    @Autowired
    private TipoCategoriaRepository tipoCategoriaRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;


    public List<TipoCategoriaDTO> obtenerTiposCategoria() {
        log.info("Obteniendo todos los tipos de categoría");
        return tipoCategoriaRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .toList();
    }

    public TipoCategoriaDTO obtenerTipoCategoriaPorId(Integer id) {
        return tipoCategoriaRepository.findById(id)
                .map(this::convertirADTO)
                .orElseThrow(() -> new RuntimeException("Tipo de categoría no encontrado con la ID: " + id));
    }

    @Transactional
    public TipoCategoriaDTO guardarTipoCategoria(TipoCategoriaDTO dto) {
        TipoCategoria tipoCategoria = new TipoCategoria();
        tipoCategoria.setNombre(dto.getNombre());
        tipoCategoria.setCategoria(buscarCategoria(dto.getCategoriaId()));

        TipoCategoria nuevoTipo = tipoCategoriaRepository.save(tipoCategoria);
        log.info("Nuevo tipo de categoría guardado exitosamente con ID: {}", nuevoTipo.getId());
        return convertirADTO(nuevoTipo);
    }

    @Transactional
    public TipoCategoriaDTO actualizarTipoCategoria(Integer id, TipoCategoriaDTO dto) {
        TipoCategoria tipoExistente = tipoCategoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo de categoría no encontrado con la ID: " + id));

        tipoExistente.setNombre(dto.getNombre());
        tipoExistente.setCategoria(buscarCategoria(dto.getCategoriaId()));

        TipoCategoria tipoActualizado = tipoCategoriaRepository.save(tipoExistente);
        log.info("Tipo de categoría con ID: {} actualizado", id);
        return convertirADTO(tipoActualizado);
    }

    @Transactional
    public void eliminarTipoCategoria(Integer id) {
        if (!tipoCategoriaRepository.existsById(id)) {
            throw new RuntimeException("Tipo de categoría no encontrado con la ID: " + id);
        }
        tipoCategoriaRepository.deleteById(id);
        log.info("Tipo de categoría con ID: {} ha sido eliminada exitosamente", id);
    }

    private Categoria buscarCategoria(Integer id) {
        if (id == null) {
            throw new RuntimeException("La categoría es obligatoria");
        }
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con la ID: " + id));
    }

    private TipoCategoriaDTO convertirADTO(TipoCategoria tipoCategoria) {
        TipoCategoriaDTO dto = new TipoCategoriaDTO();
        dto.setId(tipoCategoria.getId());
        dto.setNombre(tipoCategoria.getNombre());
        dto.setCategoriaId(tipoCategoria.getCategoria() != null ? tipoCategoria.getCategoria().getId() : null);
        return dto;
    }
}

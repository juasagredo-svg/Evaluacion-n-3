package com.Space_Movie.Movie.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Space_Movie.Movie.DTO.ImagenPeliculaDTO;
import com.Space_Movie.Movie.Model.ImagenPelicula;
import com.Space_Movie.Movie.Model.ProductoAudiovisual;
import com.Space_Movie.Movie.Repository.ImagenesPeliculasRepository;
import com.Space_Movie.Movie.Repository.ProductoAudiovisualRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
@Slf4j
public class ImagenesPeliculasService {

    @Autowired
    private ImagenesPeliculasRepository imagenesPeliculasRepository;
    @Autowired
    private ProductoAudiovisualRepository productoAudiovisualRepository;


    public List<ImagenPeliculaDTO> obtenerImagenes() {
        log.info("Obteniendo todas las imágenes de películas");
        return imagenesPeliculasRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .toList();
    }

    public ImagenPeliculaDTO obtenerImagenPorId(Integer id) {
        return imagenesPeliculasRepository.findById(id)
                .map(this::convertirADTO)
                .orElseThrow(() -> new RuntimeException("Imagen no encontrada con la ID: " + id));
    }

    @Transactional
    public ImagenPeliculaDTO guardarImagen(ImagenPeliculaDTO dto) {
        ImagenPelicula imagen = new ImagenPelicula();
        imagen.setUrl(dto.getUrl());
        imagen.setEsPrincipal(dto.getEsPrincipal());
        imagen.setProductoAudiovisual(buscarProducto(dto.getProductoAudiovisualId()));

        ImagenPelicula nuevaImagen = imagenesPeliculasRepository.save(imagen);
        log.info("Nueva imagen guardada exitosamente con ID: {}", nuevaImagen.getId());
        return convertirADTO(nuevaImagen);
    }

    @Transactional
    public ImagenPeliculaDTO actualizarImagen(Integer id, ImagenPeliculaDTO dto) {
        ImagenPelicula imagenExistente = imagenesPeliculasRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Imagen no encontrada con la ID: " + id));

        imagenExistente.setUrl(dto.getUrl());
        imagenExistente.setEsPrincipal(dto.getEsPrincipal());
        imagenExistente.setProductoAudiovisual(buscarProducto(dto.getProductoAudiovisualId()));

        ImagenPelicula imagenActualizada = imagenesPeliculasRepository.save(imagenExistente);
        log.info("Imagen con ID: {} actualizada", id);
        return convertirADTO(imagenActualizada);
    }

    @Transactional
    public void eliminarImagen(Integer id) {
        if (!imagenesPeliculasRepository.existsById(id)) {
            throw new RuntimeException("Imagen no encontrada con la ID: " + id);
        }
        imagenesPeliculasRepository.deleteById(id);
        log.info("Imagen con ID: {} ha sido eliminada exitosamente", id);
    }

    private ProductoAudiovisual buscarProducto(Integer id) {
        if (id == null) {
            throw new RuntimeException("El producto audiovisual es obligatorio");
        }
        return productoAudiovisualRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto audiovisual no encontrado con la ID: " + id));
    }

    private ImagenPeliculaDTO convertirADTO(ImagenPelicula imagen) {
        ImagenPeliculaDTO dto = new ImagenPeliculaDTO();
        dto.setId(imagen.getId());
        dto.setUrl(imagen.getUrl());
        dto.setEsPrincipal(imagen.getEsPrincipal());
        dto.setProductoAudiovisualId(
                imagen.getProductoAudiovisual() != null ? imagen.getProductoAudiovisual().getId() : null);
        return dto;
    }
}

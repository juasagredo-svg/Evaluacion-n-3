package com.Space_Movie.Movie.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Space_Movie.Movie.DTO.ProductoAudiovisualDTO;
import com.Space_Movie.Movie.Model.Categoria;
import com.Space_Movie.Movie.Model.EquipoDireccion;
import com.Space_Movie.Movie.Model.ProductoAudiovisual;
import com.Space_Movie.Movie.Repository.CategoriaRepository;
import com.Space_Movie.Movie.Repository.EquipoDireccionRepository;
import com.Space_Movie.Movie.Repository.ProductoAudiovisualRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
@Slf4j
public class ProductoAudiovisualService {

    @Autowired
    private ProductoAudiovisualRepository productoAudiovisualRepository;
    @Autowired
    private EquipoDireccionRepository equipoDireccionRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;


    public List<ProductoAudiovisualDTO> obtenerProductos() {
        log.info("Obteniendo todos los productos audiovisuales");
        return productoAudiovisualRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .toList();
    }

    public ProductoAudiovisualDTO obtenerProductoPorId(Integer id) {
        return productoAudiovisualRepository.findById(id)
                .map(this::convertirADTO)
                .orElseThrow(() -> new RuntimeException("Producto audiovisual no encontrado con la ID: " + id));
    }

    @Transactional
    public ProductoAudiovisualDTO guardarProducto(ProductoAudiovisualDTO dto) {
        ProductoAudiovisual producto = new ProductoAudiovisual();
        copiarDatos(producto, dto);
        producto.setEquipoDireccion(buscarEquipoDireccion(dto.getEquipoDireccionId()));
        producto.setCategoria(buscarCategoria(dto.getCategoriaId()));

        ProductoAudiovisual nuevoProducto = productoAudiovisualRepository.save(producto);
        log.info("Nuevo producto audiovisual guardado exitosamente con ID: {}", nuevoProducto.getId());
        return convertirADTO(nuevoProducto);
    }

    @Transactional
    public ProductoAudiovisualDTO actualizarProducto(Integer id, ProductoAudiovisualDTO dto) {
        ProductoAudiovisual productoExistente = productoAudiovisualRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto audiovisual no encontrado con la ID: " + id));

        copiarDatos(productoExistente, dto);
        productoExistente.setEquipoDireccion(buscarEquipoDireccion(dto.getEquipoDireccionId()));
        productoExistente.setCategoria(buscarCategoria(dto.getCategoriaId()));

        ProductoAudiovisual productoActualizado = productoAudiovisualRepository.save(productoExistente);
        log.info("Producto audiovisual con ID: {} actualizado", id);
        return convertirADTO(productoActualizado);
    }

    @Transactional
    public void eliminarProducto(Integer id) {
        if (!productoAudiovisualRepository.existsById(id)) {
            throw new RuntimeException("Producto audiovisual no encontrado con la ID: " + id);
        }
        productoAudiovisualRepository.deleteById(id);
        log.info("Producto audiovisual con ID: {} ha sido eliminado exitosamente", id);
    }

    private void copiarDatos(ProductoAudiovisual producto, ProductoAudiovisualDTO dto) {
        producto.setPoster(dto.getPoster());
        producto.setTitulo(dto.getTitulo());
        producto.setSinopsis(dto.getSinopsis());
        producto.setAnio(dto.getAnio());
        producto.setDuracionMinutos(dto.getDuracionMinutos());
        producto.setFechaEstreno(dto.getFechaEstreno());
        producto.setGenero(dto.getGenero());
        producto.setAprobacionPublico(dto.getAprobacionPublico());
        producto.setAprobacionCritica(dto.getAprobacionCritica());
    }

    private EquipoDireccion buscarEquipoDireccion(Integer id) {
        if (id == null) {
            throw new RuntimeException("El equipo de dirección es obligatorio");
        }
        return equipoDireccionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipo de dirección no encontrado con la ID: " + id));
    }

    private Categoria buscarCategoria(Integer id) {
        if (id == null) {
            throw new RuntimeException("La categoría es obligatoria");
        }
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con la ID: " + id));
    }

    private ProductoAudiovisualDTO convertirADTO(ProductoAudiovisual producto) {
        ProductoAudiovisualDTO dto = new ProductoAudiovisualDTO();
        dto.setId(producto.getId());
        dto.setPoster(producto.getPoster());
        dto.setTitulo(producto.getTitulo());
        dto.setSinopsis(producto.getSinopsis());
        dto.setAnio(producto.getAnio());
        dto.setDuracionMinutos(producto.getDuracionMinutos());
        dto.setFechaEstreno(producto.getFechaEstreno());
        dto.setGenero(producto.getGenero());
        dto.setAprobacionPublico(producto.getAprobacionPublico());
        dto.setAprobacionCritica(producto.getAprobacionCritica());
        dto.setEquipoDireccionId(producto.getEquipoDireccion() != null ? producto.getEquipoDireccion().getId() : null);
        dto.setCategoriaId(producto.getCategoria() != null ? producto.getCategoria().getId() : null);
        return dto;
    }
}

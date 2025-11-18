package com.tuempresa.chifawufuproyecto.service;

import com.tuempresa.chifawufuproyecto.model.Producto;
import com.tuempresa.chifawufuproyecto.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> listarTodosLosProductos() {
        return productoRepository.findAll();
    }
    
    public void guardarProducto(Producto producto) {
        productoRepository.save(producto);
    }

    public Optional<Producto> buscarProductoPorId(Long id) {
        return productoRepository.findById(id);
    }

    public void eliminarProductoPorId(Long id) {
        productoRepository.deleteById(id);
    }

    // --- MÉTODO NUEVO AÑADIDO ---
    // Devuelve solo productos vendibles (activos y con stock)
    public List<Producto> listarProductosParaVenta() {
        return productoRepository.findByActivoTrueAndStockGreaterThan(0);
    }
}
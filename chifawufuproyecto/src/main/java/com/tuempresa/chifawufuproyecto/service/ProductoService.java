package com.tuempresa.chifawufuproyecto.service;

import com.tuempresa.chifawufuproyecto.model.Producto;
import com.tuempresa.chifawufuproyecto.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional; // <-- IMPORTANTE: Añade esta importación

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

    // --- MÉTODO NUEVO PARA EDITAR ---
    public Optional<Producto> buscarProductoPorId(Long id) {
        return productoRepository.findById(id);
    }

    // --- MÉTODO NUEVO PARA ELIMINAR ---
    public void eliminarProductoPorId(Long id) {
        productoRepository.deleteById(id);
    }
}
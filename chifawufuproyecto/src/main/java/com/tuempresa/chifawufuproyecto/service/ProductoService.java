package com.tuempresa.chifawufuproyecto.service;

import com.tuempresa.chifawufuproyecto.model.Producto;
import com.tuempresa.chifawufuproyecto.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    // Inyectamos nuestro DAO (Repositorio)
    @Autowired
    private ProductoRepository productoRepository;

    // Método para obtener todos los productos de la BD
    public List<Producto> listarTodosLosProductos() {
        return productoRepository.findAll();
    }
    
public void guardarProducto(Producto producto) {
        productoRepository.save(producto);
    }
}
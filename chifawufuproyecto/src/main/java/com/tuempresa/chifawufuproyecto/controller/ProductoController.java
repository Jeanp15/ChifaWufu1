package com.tuempresa.chifawufuproyecto.controller;

import com.tuempresa.chifawufuproyecto.model.Producto;
import com.tuempresa.chifawufuproyecto.service.ProductoService; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; 
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

// --- NUEVAS IMPORTACIONES AÑADIDAS ---
import org.springframework.web.bind.annotation.PathVariable;
import java.util.Optional;

import java.util.List;

@Controller
public class ProductoController {

    @Autowired
    private ProductoService productoService; 

    @GetMapping("/productos")
    public String verPaginaDeProductos(Model model) {
        List<Producto> listaProductos = productoService.listarTodosLosProductos();
        model.addAttribute("listaProductos", listaProductos);
        return "productos"; 
    }

    @GetMapping("/productos/nuevo")
    public String mostrarFormularioDeNuevoProducto(Model model) {
        Producto producto = new Producto();
        model.addAttribute("producto", producto);
        return "producto-formulario";
    }

    @PostMapping("/productos/guardar")
    public String guardarProducto(@ModelAttribute("producto") Producto producto) {
        productoService.guardarProducto(producto);
        return "redirect:/productos";
    }

    // --- MÉTODO NUEVO PARA MOSTRAR EDICIÓN (PASO 9) ---
    @GetMapping("/productos/editar/{id}")
    public String mostrarFormularioDeEditarProducto(@PathVariable Long id, Model model) {
        
        Optional<Producto> productoOptional = productoService.buscarProductoPorId(id);
        
        if (productoOptional.isPresent()) {
            model.addAttribute("producto", productoOptional.get());
            return "producto-formulario"; // Reutilizamos el formulario
        } else {
            return "redirect:/productos";
        }
    }

    // --- MÉTODO NUEVO PARA ELIMINAR (PASO 10) ---
    @GetMapping("/productos/eliminar/{id}")
    public String eliminarProducto(@PathVariable Long id) {
        
        // Usamos el servicio para eliminar el producto por su ID
        productoService.eliminarProductoPorId(id);
        
        // Redirigimos de vuelta a la lista de productos
        return "redirect:/productos";
    }
}
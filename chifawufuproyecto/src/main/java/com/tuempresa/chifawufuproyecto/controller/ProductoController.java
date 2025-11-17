package com.tuempresa.chifawufuproyecto.controller;

import com.tuempresa.chifawufuproyecto.model.Producto;
import com.tuempresa.chifawufuproyecto.service.ProductoService; // Importamos el Servicio
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; // Para enviar datos a la Vista
import org.springframework.web.bind.annotation.GetMapping; // Para manejar peticiones GET
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.List;

@Controller // ¡Importante! Usamos @Controller, NO @RestController
public class ProductoController {

    @Autowired
    private ProductoService productoService; // Inyectamos el Servicio
    /**
     * Este método manejará las peticiones a la URL "/productos"
     */
    @GetMapping("/productos")
    public String verPaginaDeProductos(Model model) {
        // 1. Obtenemos la lista de productos desde el servicio (que llama al DAO)
        List<Producto> listaProductos = productoService.listarTodosLosProductos();
        // 2. "Ponemos" esa lista en el "Model" para que la Vista la pueda usar
        //    Le damos el nombre "listaProductos"
        model.addAttribute("listaProductos", listaProductos);
        return "productos"; 
    }
    // --- NUEVO MÉTODO 1: Mostrar el formulario ---
    /**
     * Maneja la petición a "/productos/nuevo"
     * Muestra la página del formulario para crear un nuevo producto.
     */
    @GetMapping("/productos/nuevo")
    public String mostrarFormularioDeNuevoProducto(Model model) {
        // Creamos un objeto Producto vacío para enlazarlo al formulario
        Producto producto = new Producto();
        model.addAttribute("producto", producto);
        
        // Devolvemos el nombre del NUEVO archivo HTML que crearemos
        return "producto-formulario";
    }

    // --- NUEVO MÉTODO 2: Guardar el formulario ---
    /**
     * Maneja la petición POST a "/productos/guardar"
     * Recibe el objeto Producto rellenado desde el formulario.
     */
    @PostMapping("/productos/guardar")
    public String guardarProducto(@ModelAttribute("producto") Producto producto) {
        // Guardamos el producto en la base de datos usando el servicio
        productoService.guardarProducto(producto);
        
        // Redirigimos al usuario de vuelta a la lista de productos
        return "redirect:/productos";
    }
}
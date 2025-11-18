package com.tuempresa.chifawufuproyecto.controller;

import com.tuempresa.chifawufuproyecto.dto.VentaFormDTO;
import com.tuempresa.chifawufuproyecto.model.Cliente;
import com.tuempresa.chifawufuproyecto.model.Producto;
import com.tuempresa.chifawufuproyecto.service.ClienteService;
import com.tuempresa.chifawufuproyecto.service.ProductoService;
import com.tuempresa.chifawufuproyecto.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class VentaController {

    @Autowired private VentaService ventaService;
    @Autowired private ProductoService productoService;
    @Autowired private ClienteService clienteService;

    // Muestra la página principal del Punto de Venta (POS)
    @GetMapping("/pos")
    public String mostrarFormularioPOS(Model model) {
        
        // 1. Cargar productos (solo activos y con stock > 0)
        List<Producto> productosDisponibles = productoService.listarProductosParaVenta();
        
        // 2. Cargar todos los clientes
        List<Cliente> clientes = clienteService.listarTodosLosClientes();

        // 3. Añadir los datos al modelo para que la Vista los use
        model.addAttribute("listaProductos", productosDisponibles);
        model.addAttribute("listaClientes", clientes);
        
        // 4. Añadir el DTO vacío para el formulario (si no existe uno de un error anterior)
        if (!model.containsAttribute("ventaForm")) {
            model.addAttribute("ventaForm", new VentaFormDTO());
        }

        return "pos-formulario"; // -> Llama a pos-formulario.html
    }

    // Procesa el formulario del POS y registra la venta
    @PostMapping("/pos/guardar")
    public String registrarVenta(@ModelAttribute("ventaForm") VentaFormDTO ventaForm,
                                 RedirectAttributes redirectAttributes) {
        try {
            // Llama al servicio que valida stock y guarda todo
            ventaService.registrarVenta(ventaForm);
            
            // Si todo sale bien, mandamos un mensaje de éxito
            redirectAttributes.addFlashAttribute("globalSuccess", "¡Venta registrada exitosamente!");
            return "redirect:/pos";

        } catch (Exception e) {
            // Si algo falla (ej. sin stock), volvemos al formulario
            // y mandamos un mensaje de error.
            redirectAttributes.addFlashAttribute("globalError", e.getMessage());
            // Devolvemos el formulario con los datos que el usuario ya había llenado
            redirectAttributes.addFlashAttribute("ventaForm", ventaForm); 
            return "redirect:/pos";
        }
    }
}
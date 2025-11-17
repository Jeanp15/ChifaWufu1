package com.tuempresa.chifawufuproyecto.controller;

import com.tuempresa.chifawufuproyecto.model.Cliente;
import com.tuempresa.chifawufuproyecto.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    // 1. LEER (Listar todos)
    @GetMapping("/clientes")
    public String verPaginaDeClientes(Model model) {
        List<Cliente> listaClientes = clienteService.listarTodosLosClientes();
        model.addAttribute("listaClientes", listaClientes);
        return "clientes"; // -> Llama a clientes.html
    }

    // 2. CREAR (Mostrar formulario)
    @GetMapping("/clientes/nuevo")
    public String mostrarFormularioDeNuevoCliente(Model model) {
        Cliente cliente = new Cliente();
        model.addAttribute("cliente", cliente);
        return "cliente-formulario"; // -> Llama a cliente-formulario.html
    }

    // 3. GUARDAR (Procesar el formulario de Crear y Modificar)
    @PostMapping("/clientes/guardar")
    public String guardarCliente(@ModelAttribute("cliente") Cliente cliente) {
        clienteService.guardarCliente(cliente);
        return "redirect:/clientes";
    }

    // 4. MODIFICAR (Mostrar formulario con datos)
    @GetMapping("/clientes/editar/{id}")
    public String mostrarFormularioDeEditarCliente(@PathVariable Long id, Model model) {
        Optional<Cliente> clienteOpt = clienteService.buscarClientePorId(id);
        if (clienteOpt.isPresent()) {
            model.addAttribute("cliente", clienteOpt.get());
            return "cliente-formulario"; // Reutiliza el mismo formulario
        }
        return "redirect:/clientes";
    }

    // 5. ELIMINAR
    @GetMapping("/clientes/eliminar/{id}")
    public String eliminarCliente(@PathVariable Long id) {
        clienteService.eliminarClientePorId(id);
        return "redirect:/clientes";
    }
}
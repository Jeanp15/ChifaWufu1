package com.tuempresa.chifawufuproyecto.controller;

import com.tuempresa.chifawufuproyecto.model.Usuario;
import com.tuempresa.chifawufuproyecto.service.UsuarioService;
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
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // --- NUEVO MÉTODO PARA MOSTRAR EL LOGIN ---
    /**
     * Muestra la página de login personalizada.
     * Spring redirigirá a esta URL automáticamente.
     */
    @GetMapping("/login")
    public String mostrarPaginaDeLogin() {
        return "login"; // -> Llama a login.html
    }

    // 1. LEER (Listar todos)
    @GetMapping("/usuarios")
    public String verPaginaDeUsuarios(Model model) {
        List<Usuario> listaUsuarios = usuarioService.listarTodosLosUsuarios();
        model.addAttribute("listaUsuarios", listaUsuarios);
        return "usuarios"; // -> Llama a usuarios.html
    }

    // 2. CREAR (Mostrar formulario)
    @GetMapping("/usuarios/nuevo")
    public String mostrarFormularioDeNuevoUsuario(Model model) {
        Usuario usuario = new Usuario();
        model.addAttribute("usuario", usuario);
        return "usuario-formulario"; // -> Llama a usuario-formulario.html
    }

    // 3. GUARDAR (Procesar el formulario de Crear y Modificar)
    @PostMapping("/usuarios/guardar")
    public String guardarUsuario(@ModelAttribute("usuario") Usuario usuario) {
        usuarioService.guardarUsuario(usuario);
        return "redirect:/usuarios";
    }

    // 4. MODIFICAR (Mostrar formulario con datos)
    @GetMapping("/usuarios/editar/{id}")
    public String mostrarFormularioDeEditarUsuario(@PathVariable Long id, Model model) {
        Optional<Usuario> usuarioOpt = usuarioService.buscarUsuarioPorId(id);
        if (usuarioOpt.isPresent()) {
            model.addAttribute("usuario", usuarioOpt.get());
            return "usuario-formulario"; // Reutiliza el mismo formulario
        }
        return "redirect:/usuarios";
    }

    // 5. ELIMINAR
    @GetMapping("/usuarios/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuarioPorId(id);
        return "redirect:/usuarios";
    }
}
package com.tuempresa.chifawufuproyecto.service;

import com.tuempresa.chifawufuproyecto.model.Usuario;
import com.tuempresa.chifawufuproyecto.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// --- NUEVAS IMPORTACIONES ---
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // --- AÑADIMOS EL ENCRIPTADOR ---
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Usuario> listarTodosLosUsuarios() {
        return usuarioRepository.findAll();
    }

    // --- MÉTODO "guardarUsuario" ACTUALIZADO ---
    public void guardarUsuario(Usuario usuario) {
        
        // Verificamos si la contraseña se está estableciendo (no está vacía)
        if (usuario.getContraseña() != null && !usuario.getContraseña().isEmpty()) {
            
            // Si el usuario es nuevo O está cambiando su contraseña,
            // la encriptamos antes de guardarla.
            String contraseñaEncriptada = passwordEncoder.encode(usuario.getContraseña());
            usuario.setContraseña(contraseñaEncriptada);
            
        } else if (usuario.getIdUsuario() != null) {
            
            // Si es un usuario existente Y la contraseña está vacía,
            // significa que no queremos cambiarla.
            // Le volvemos a poner la contraseña antigua (ya encriptada) de la BD.
            Optional<Usuario> usuarioExistente = usuarioRepository.findById(usuario.getIdUsuario());
            usuarioExistente.ifPresent(u -> usuario.setContraseña(u.getContraseña()));
        }

        usuarioRepository.save(usuario);
    }

    public Optional<Usuario> buscarUsuarioPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public void eliminarUsuarioPorId(Long id) {
        usuarioRepository.deleteById(id);
    }

    public Optional<Usuario> buscarPorNombreUsuario(String nombreUsuario) {
        return usuarioRepository.findByNombreUsuario(nombreUsuario);
    }
}
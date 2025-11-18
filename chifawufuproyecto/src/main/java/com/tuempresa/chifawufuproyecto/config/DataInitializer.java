package com.tuempresa.chifawufuproyecto.config;

import com.tuempresa.chifawufuproyecto.model.Usuario;
import com.tuempresa.chifawufuproyecto.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    // 1. Declaramos las dependencias como 'final'
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    // 2. Creamos el constructor pidiendo las dependencias
    public DataInitializer(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        
        if (usuarioRepository.findByNombreUsuario("admin").isEmpty()) {
            
            System.out.println("--- Creando usuario 'admin' por defecto ---");
            Usuario admin = new Usuario();
            admin.setNombreUsuario("admin");
            admin.setContraseña(passwordEncoder.encode("1234")); // Encriptamos
            admin.setRol("Administrador");
            admin.setActivo(true);
            
            usuarioRepository.save(admin);
            System.out.println("--- Usuario 'admin' creado con contraseña '1234' ---");
        } else {
            System.out.println("--- El usuario 'admin' ya existe. No se crea. ---");
        }
    }
}
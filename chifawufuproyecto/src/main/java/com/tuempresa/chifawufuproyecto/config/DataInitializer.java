package com.tuempresa.chifawufuproyecto.config;

import com.tuempresa.chifawufuproyecto.model.Usuario;
import com.tuempresa.chifawufuproyecto.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        
        // 1. Buscamos si el usuario 'admin' ya existe
        if (usuarioRepository.findByNombreUsuario("admin").isEmpty()) {
            
            System.out.println("--- Creando usuario 'admin' por defecto ---");

            // 2. Si no existe, lo creamos
            Usuario admin = new Usuario();
            admin.setNombreUsuario("admin");
            
            // 3. Encriptamos la contraseña '1234'
            admin.setContraseña(passwordEncoder.encode("1234"));
            
            admin.setRol("Administrador"); // Le damos el rol
            admin.setActivo(true);
            
            // 4. Guardamos el usuario en la BD
            usuarioRepository.save(admin);
            
            System.out.println("--- Usuario 'admin' creado con contraseña '1234' ---");
        } else {
            System.out.println("--- El usuario 'admin' ya existe. No se crea. ---");
        }
    }
}
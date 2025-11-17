package com.tuempresa.chifawufuproyecto.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration; // <-- ¡ESTA ERA LA ANOTACIÓN FALTANTE!
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration // <-- ¡Asegúrate de que esta línea esté aquí!
public class SecurityConfig {

    /**
     * Esta anotación @Bean le dice a Spring:
     * "¡Crea este objeto y ponlo a disposición de quien lo pida!"
     * Esto es lo que resuelve el error.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Más adelante, aquí mismo configuraremos las reglas de login y permisos.
}
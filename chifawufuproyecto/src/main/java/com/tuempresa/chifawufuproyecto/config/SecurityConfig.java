package com.tuempresa.chifawufuproyecto.config;

import com.tuempresa.chifawufuproyecto.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        
        AuthenticationManagerBuilder authBuilder = 
            http.getSharedObject(AuthenticationManagerBuilder.class);
        
        authBuilder.userDetailsService(customUserDetailsService)
                   .passwordEncoder(passwordEncoder());
        
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/css/**", "/js/**").permitAll()
                .requestMatchers("/productos/**", "/clientes/**", "/usuarios/**").hasRole("Administrador")
                
                // --- LÍNEA NUEVA AÑADIDA ---
                // Damos permiso a Cajeros, Mozos y Administradores para acceder al POS
                .requestMatchers("/pos", "/pos/guardar").hasAnyRole("Cajero", "Mozo", "Administrador")

                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login-process") 
                .defaultSuccessUrl("/pos", true) // Cambiado para ir al POS después de login
                .failureUrl("/login?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout") 
                .logoutSuccessUrl("/login?logout=true")
                .permitAll()
            );

        return http.build();
    }
}
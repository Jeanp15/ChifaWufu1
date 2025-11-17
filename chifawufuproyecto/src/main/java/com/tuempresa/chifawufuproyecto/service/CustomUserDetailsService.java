package com.tuempresa.chifawufuproyecto.service;

import com.tuempresa.chifawufuproyecto.model.Usuario;
import com.tuempresa.chifawufuproyecto.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository; // Nuestro DAO de Usuario

    /**
     * Spring Security llamará a este método cuando alguien intente iniciar sesión.
     */
    @Override
    public UserDetails loadUserByUsername(String nombreUsuario) throws UsernameNotFoundException {
        
        // 1. Buscamos al usuario en nuestra BD usando el DAO
        Usuario usuario = usuarioRepository.findByNombreUsuario(nombreUsuario)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + nombreUsuario));

        // 2. Creamos la lista de "roles" (autoridades) para Spring Security
        Set<GrantedAuthority> authorities = new HashSet<>();
        
        // Le añadimos el rol, pero con el prefijo "ROLE_" (es un requisito de Spring)
        authorities.add(new SimpleGrantedAuthority("ROLE_" + usuario.getRol()));

        // 3. Devolvemos el "UserDetails" que Spring Security entiende
        //    (usamos el 'User' de Spring Security, no el nuestro)
        return new User(
            usuario.getNombreUsuario(),    // El nombre de usuario
            usuario.getContraseña(),       // La contraseña YA ENCRIPTADA de la BD
            authorities                    // Los roles (ej. ROLE_Administrador)
        );
    }
}
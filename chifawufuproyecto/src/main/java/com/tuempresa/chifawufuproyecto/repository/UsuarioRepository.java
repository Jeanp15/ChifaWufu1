package com.tuempresa.chifawufuproyecto.repository;

import com.tuempresa.chifawufuproyecto.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional; // Importante para búsquedas que pueden no devolver nada

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // JpaRepository<Usuario, Long>
    // 1. "Usuario": La entidad que manejará.
    // 2. "Long": El tipo de dato de la clave primaria (@Id) de Usuario (el idUsuario).

    /**
     * ¡Este es nuestro primer método DAO personalizado!
     * * Spring Data JPA es lo suficientemente inteligente como para entender
     * "findByNombreUsuario". Automáticamente escribirá el SQL:
     * "SELECT * FROM usuario WHERE nombre_usuario = ?"
     * * Lo usaremos para el Login.
     */
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);
}
package com.tuempresa.chifawufuproyecto.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List; // Importamos la lista para las ventas

@Data // Crea TODOS los getters, setters, toString, etc.
@NoArgsConstructor // Crea el constructor vacío para JPA
@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    @Column(nullable = false, unique = true, length = 80)
    private String nombreUsuario;

    @Column(nullable = false, length = 100)
    private String contraseña;

    @Column(nullable = false, length = 20)
    private String rol; // Ej. "Administrador", "Cajero"

    @Column(nullable = false)
    private Boolean activo = true;

    // --- Relaciones (Según tu PDF) ---
    
    /**
     * Un Usuario (ej. Cajero) puede registrar MUCHAS Ventas.
     * "mappedBy" le dice a JPA que la entidad 'Venta' es la dueña
     * de esta relación (la venta tendrá un campo 'usuario').
     */
    @OneToMany(mappedBy = "usuario")
    private List<Venta> ventas;

    // ¡Y listo! No más getters ni setters manuales.
}
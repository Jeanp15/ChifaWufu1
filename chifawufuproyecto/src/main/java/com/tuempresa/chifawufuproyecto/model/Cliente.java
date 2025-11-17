package com.tuempresa.chifawufuproyecto.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCliente;

    @Column(nullable = false)
    private String nombre;

    @Column(unique = true)
    private String dniRuc; // Como en tu PDF

    private String telefono;
    
    private String direccion;

    /**
     * Relación: Un Cliente puede tener MUCHAS Ventas.
     * 'mappedBy = "cliente"' indica que la clase Venta
     * es la dueña de la relación.
     */
    @OneToMany(mappedBy = "cliente")
    private List<Venta> ventas;
}
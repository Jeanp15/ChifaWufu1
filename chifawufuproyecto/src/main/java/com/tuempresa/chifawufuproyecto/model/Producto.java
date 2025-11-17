package com.tuempresa.chifawufuproyecto.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.Data;
import lombok.NoArgsConstructor; 

@Data 
@NoArgsConstructor
@Entity
@Table(name = "producto")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProducto;

    @Column(nullable = false, length = 80)
    private String nombre;

    @Column(length = 120)
    private String descripcion;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    @Column(length = 50)
    private String categoria;

    @Column(nullable = false)
    private Boolean activo = true;

    @Column(nullable = false)
    private Integer stock = 0;

}
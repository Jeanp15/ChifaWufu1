package com.tuempresa.chifawufuproyecto.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Entity
@Table(name = "detalle_venta")
public class DetalleVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDetalle;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precioUnitario; // Guardamos el precio al momento de la venta

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal; // Es (cantidad * precioUnitario)

    // --- RELACIONES ---

    /**
     * Relación Muchos-a-Uno: MUCHOS Detalles pertenecen a UNA Venta.
     */
    @ManyToOne
    @JoinColumn(name = "id_venta", nullable = false)
    private Venta venta;

    /**
     * Relación Muchos-a-Uno: MUCHOS Detalles (líneas de venta)
     * pueden apuntar a UN Producto.
     */
    @ManyToOne
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto producto;
}
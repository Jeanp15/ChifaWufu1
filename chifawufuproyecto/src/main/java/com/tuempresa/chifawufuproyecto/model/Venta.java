package com.tuempresa.chifawufuproyecto.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "venta")
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVenta;

    @Column(nullable = false)
    private LocalDateTime fecha = LocalDateTime.now();

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

    @Column(nullable = false)
    private String tipoComprobante; // "Boleta" o "Factura"

    // --- RELACIONES ---

    /**
     * Relación Muchos-a-Uno: MUCHAS Ventas pertenecen a UN Usuario (Cajero).
     */
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    /**
     * Relación Muchos-a-Uno: MUCHAS Ventas pueden pertenecer a UN Cliente.
     * (Es 'nullable' porque para una boleta simple, no necesitamos cliente).
     */
    @ManyToOne
    @JoinColumn(name = "id_cliente") // Nota: no es 'nullable = false'
    private Cliente cliente;

    /**
     * Relación Uno-a-Muchos: UNA Venta tiene MUCHOS Detalles (productos).
     * CascadeType.ALL: Si se borra esta Venta, se borran sus detalles.
     */
    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL)
    private List<DetalleVenta> detalles;
}
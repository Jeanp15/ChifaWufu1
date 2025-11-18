package com.tuempresa.chifawufuproyecto.dto;

import com.tuempresa.chifawufuproyecto.model.Venta;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

// DTO para transportar el resumen del cierre de caja
@Data
public class CierreCajaDTO {
    private long totalTransacciones;
    private BigDecimal totalGeneral;
    private BigDecimal totalEfectivo;
    private BigDecimal totalTarjeta;
    private BigDecimal totalYape;
    private BigDecimal totalPlin;
    private BigDecimal totalOtros;
    private List<Venta> ventasDelDia; // Lista de ventas para detalle

    public CierreCajaDTO(List<Venta> ventasDelDia) {
        this.ventasDelDia = ventasDelDia;
        this.totalTransacciones = ventasDelDia.size();
        
        // Inicializamos todos los totales en CERO
        this.totalGeneral = BigDecimal.ZERO;
        this.totalEfectivo = BigDecimal.ZERO;
        this.totalTarjeta = BigDecimal.ZERO;
        this.totalYape = BigDecimal.ZERO;
        this.totalPlin = BigDecimal.ZERO;
        this.totalOtros = BigDecimal.ZERO;

        // Calculamos los totales recorriendo la lista
        for (Venta venta : ventasDelDia) {
            this.totalGeneral = this.totalGeneral.add(venta.getTotal());
            
            if (venta.getMetodoDePago() == null) {
                 this.totalOtros = this.totalOtros.add(venta.getTotal());
                 continue; // Evita error si una venta antigua no tiene método de pago
            }
            
            switch (venta.getMetodoDePago()) {
                case "Efectivo":
                    this.totalEfectivo = this.totalEfectivo.add(venta.getTotal());
                    break;
                case "Tarjeta":
                    this.totalTarjeta = this.totalTarjeta.add(venta.getTotal());
                    break;
                case "Yape":
                    this.totalYape = this.totalYape.add(venta.getTotal());
                    break;
                case "Plin":
                    this.totalPlin = this.totalPlin.add(venta.getTotal());
                    break;
                default:
                    this.totalOtros = this.totalOtros.add(venta.getTotal());
                    break;
            }
        }
    }
}
package com.tuempresa.chifawufuproyecto.dto;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

// DTO para el formulario completo (el carrito)
@Data
public class VentaFormDTO {

    private Long idCliente;
    private String tipoComprobante;
    
    // --- CAMPO NUEVO AÑADIDO ---
    private String metodoDePago;

    private List<VentaDetalleDTO> detalles = new ArrayList<>();

    public VentaFormDTO() {
        // Añadimos una fila vacía por defecto
        this.detalles.add(new VentaDetalleDTO());
    }
}
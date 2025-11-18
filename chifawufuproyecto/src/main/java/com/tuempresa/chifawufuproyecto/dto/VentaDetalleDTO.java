package com.tuempresa.chifawufuproyecto.dto;

import lombok.Data;

@Data
public class VentaDetalleDTO {
    private Long idProducto;
    private Integer cantidad;
    // No necesitamos más, el precio lo sacamos de la BD
}
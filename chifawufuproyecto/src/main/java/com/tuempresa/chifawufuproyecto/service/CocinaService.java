package com.tuempresa.chifawufuproyecto.service;

import com.tuempresa.chifawufuproyecto.model.Venta;
import com.tuempresa.chifawufuproyecto.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CocinaService {

    @Autowired
    private VentaRepository ventaRepository;

    /**
     * Obtiene todos los pedidos que están en estado "PENDIENTE"
     */
    public List<Venta> listarPedidosPendientes() {
        // Usamos el nuevo método del DAO
        return ventaRepository.findByEstadoOrderByFechaAsc("PENDIENTE");
    }

    /**
     * Cambia el estado de un pedido (ej. de "PENDIENTE" a "LISTO")
     */
    public void actualizarEstadoPedido(Long idVenta, String nuevoEstado) {
        Venta venta = ventaRepository.findById(idVenta)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        
        venta.setEstado(nuevoEstado);
        ventaRepository.save(venta);
    }
}
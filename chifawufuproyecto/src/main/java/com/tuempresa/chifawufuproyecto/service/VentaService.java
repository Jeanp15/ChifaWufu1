package com.tuempresa.chifawufuproyecto.service;

import com.tuempresa.chifawufuproyecto.dto.VentaDetalleDTO;
import com.tuempresa.chifawufuproyecto.dto.VentaFormDTO;
import com.tuempresa.chifawufuproyecto.model.*;
import com.tuempresa.chifawufuproyecto.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // ¡Importante!
import java.time.LocalDate;       
import java.time.LocalDateTime; 
import java.time.LocalTime;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class VentaService {

    @Autowired private VentaRepository ventaRepository;
    @Autowired private DetalleVentaRepository detalleVentaRepository;
    @Autowired private ProductoRepository productoRepository;
    @Autowired private ClienteRepository clienteRepository;
    @Autowired private UsuarioRepository usuarioRepository;

    /**
     * @Transactional asegura que si algo falla (ej. no hay stock),
     * toda la operación se revierte (no se guarda nada).
     */
    @Transactional(rollbackFor = Exception.class) // Revertir ante CUALQUIER error
    public Venta registrarVenta(VentaFormDTO ventaForm) throws Exception {

        // 1. Obtener al Cajero/Usuario que está logueado
        String nombreUsuarioLogueado = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioLogueado = usuarioRepository.findByNombreUsuario(nombreUsuarioLogueado)
                .orElseThrow(() -> new RuntimeException("Error: Usuario no encontrado"));

        // 2. Obtener al Cliente (si se seleccionó uno)
        Cliente clienteVenta = null;
        if (ventaForm.getIdCliente() != null) {
            clienteVenta = clienteRepository.findById(ventaForm.getIdCliente()).orElse(null);
        }

        // 3. Crear la cabecera de la Venta
        Venta nuevaVenta = new Venta();
        nuevaVenta.setUsuario(usuarioLogueado);
        nuevaVenta.setCliente(clienteVenta);
        nuevaVenta.setTipoComprobante(ventaForm.getTipoComprobante());
        nuevaVenta.setTotal(BigDecimal.ZERO); // Se calcula después
        
        nuevaVenta = ventaRepository.save(nuevaVenta); // Guardamos para obtener ID

        BigDecimal totalVenta = BigDecimal.ZERO;
        List<DetalleVenta> detallesParaGuardar = new ArrayList<>();

        // 4. Recorrer el "carrito" (detalles del DTO)
        for (VentaDetalleDTO detalleDTO : ventaForm.getDetalles()) {
            if (detalleDTO.getIdProducto() == null || detalleDTO.getCantidad() == null || detalleDTO.getCantidad() <= 0) {
                continue; // Ignorar filas vacías
            }

            // 5. Buscar el producto
            Producto producto = productoRepository.findById(detalleDTO.getIdProducto())
                    .orElseThrow(() -> new RuntimeException("Error: Producto no encontrado"));

            // 6. Validar Stock
            if (producto.getStock() < detalleDTO.getCantidad()) {
                throw new Exception("No hay stock suficiente para: " + producto.getNombre());
            }

            // 7. Crear el Detalle de Venta
            DetalleVenta detalle = new DetalleVenta();
            detalle.setVenta(nuevaVenta); // Asociar con la Venta
            detalle.setProducto(producto);
            detalle.setCantidad(detalleDTO.getCantidad());
            detalle.setPrecioUnitario(producto.getPrecio());
            detalle.setSubtotal(producto.getPrecio().multiply(BigDecimal.valueOf(detalleDTO.getCantidad())));
            
            detallesParaGuardar.add(detalle);

            // 8. Sumar al total
            totalVenta = totalVenta.add(detalle.getSubtotal());

            // 9. Descontar Stock
            producto.setStock(producto.getStock() - detalleDTO.getCantidad());
            productoRepository.save(producto);
        }

        if (detallesParaGuardar.isEmpty()) {
            throw new RuntimeException("No se puede registrar una venta sin productos.");
        }

        // 10. Guardar detalles y actualizar total de la Venta
        detalleVentaRepository.saveAll(detallesParaGuardar);
        nuevaVenta.setDetalles(detallesParaGuardar);
        nuevaVenta.setTotal(totalVenta);
        
        return ventaRepository.save(nuevaVenta); // Guardamos Venta con total actualizado
    }
}
package com.tuempresa.chifawufuproyecto.service;

import com.tuempresa.chifawufuproyecto.dto.CierreCajaDTO;
import com.tuempresa.chifawufuproyecto.dto.VentaDetalleDTO;
import com.tuempresa.chifawufuproyecto.dto.VentaFormDTO;
import com.tuempresa.chifawufuproyecto.model.*;
import com.tuempresa.chifawufuproyecto.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;       
import java.time.LocalDateTime; 
import java.time.LocalTime;       
import java.util.ArrayList;
import java.util.List;

@Service
public class VentaService {

    @Autowired private VentaRepository ventaRepository;
    @Autowired private DetalleVentaRepository detalleVentaRepository;
    @Autowired private ProductoRepository productoRepository;
    @Autowired private ClienteRepository clienteRepository;
    @Autowired private UsuarioRepository usuarioRepository;

    @Transactional(rollbackFor = Exception.class)
    public Venta registrarVenta(VentaFormDTO ventaForm) throws Exception {

        String nombreUsuarioLogueado = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuarioLogueado = usuarioRepository.findByNombreUsuario(nombreUsuarioLogueado)
                .orElseThrow(() -> new RuntimeException("Error: Usuario no encontrado"));

        Cliente clienteVenta = null;
        if (ventaForm.getIdCliente() != null) {
            clienteVenta = clienteRepository.findById(ventaForm.getIdCliente()).orElse(null);
        }

        Venta nuevaVenta = new Venta();
        nuevaVenta.setUsuario(usuarioLogueado);
        nuevaVenta.setCliente(clienteVenta);
        nuevaVenta.setTipoComprobante(ventaForm.getTipoComprobante());
        nuevaVenta.setMetodoDePago(ventaForm.getMetodoDePago());
        
        // --- LÍNEA NUEVA AÑADIDA ---
        nuevaVenta.setEstado("PENDIENTE"); // El estado inicial de toda venta
        
        nuevaVenta.setTotal(BigDecimal.ZERO);
        nuevaVenta = ventaRepository.save(nuevaVenta);

        BigDecimal totalVenta = BigDecimal.ZERO;
        List<DetalleVenta> detallesParaGuardar = new ArrayList<>();

        for (VentaDetalleDTO detalleDTO : ventaForm.getDetalles()) {
            if (detalleDTO.getIdProducto() == null || detalleDTO.getCantidad() == null || detalleDTO.getCantidad() <= 0) {
                continue; 
            }
            Producto producto = productoRepository.findById(detalleDTO.getIdProducto())
                    .orElseThrow(() -> new RuntimeException("Error: Producto no encontrado"));
            if (producto.getStock() < detalleDTO.getCantidad()) {
                throw new Exception("No hay stock suficiente para: " + producto.getNombre());
            }
            DetalleVenta detalle = new DetalleVenta();
            detalle.setVenta(nuevaVenta); 
            detalle.setProducto(producto);
            detalle.setCantidad(detalleDTO.getCantidad());
            detalle.setPrecioUnitario(producto.getPrecio()); 
            detalle.setSubtotal(producto.getPrecio().multiply(BigDecimal.valueOf(detalleDTO.getCantidad())));
            detallesParaGuardar.add(detalle);
            totalVenta = totalVenta.add(detalle.getSubtotal());
            producto.setStock(producto.getStock() - detalleDTO.getCantidad());
            productoRepository.save(producto); 
        }

        if (detallesParaGuardar.isEmpty()) {
            throw new RuntimeException("No se puede registrar una venta sin productos.");
        }

        detalleVentaRepository.saveAll(detallesParaGuardar);
        nuevaVenta.setDetalles(detallesParaGuardar); 
        nuevaVenta.setTotal(totalVenta);
        
        return ventaRepository.save(nuevaVenta); 
    }

    public List<Venta> buscarVentasPorRangoDeFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        LocalDateTime inicioDelDia = fechaInicio.atStartOfDay();
        LocalDateTime finDelDia = fechaFin.atTime(LocalTime.MAX);
        return ventaRepository.findByFechaBetween(inicioDelDia, finDelDia);
    }
    
    public CierreCajaDTO realizarCierreCaja(LocalDate fecha) {
        List<Venta> ventasDelDia = buscarVentasPorRangoDeFechas(fecha, fecha);
        CierreCajaDTO cierre = new CierreCajaDTO(ventasDelDia);
        return cierre;
    }
}
package com.tuempresa.chifawufuproyecto.controller;

import com.tuempresa.chifawufuproyecto.dto.CierreCajaDTO;
import com.tuempresa.chifawufuproyecto.model.Venta;
import com.tuempresa.chifawufuproyecto.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Controller
public class ReporteController {

    @Autowired
    private VentaService ventaService;

    // Para CU08: Reporte General
    @GetMapping("/reportes")
    public String mostrarReporteDeVentas(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
            Model model) {

        if (fechaInicio == null) {
            fechaInicio = LocalDate.now();
        }
        if (fechaFin == null) {
            fechaFin = LocalDate.now();
        }

        List<Venta> ventas = ventaService.buscarVentasPorRangoDeFechas(fechaInicio, fechaFin);
        BigDecimal totalGeneral = ventas.stream()
                                    .map(Venta::getTotal)
                                    .reduce(BigDecimal.ZERO, BigDecimal::add);

        model.addAttribute("listaVentas", ventas);
        model.addAttribute("totalGeneral", totalGeneral);
        model.addAttribute("fechaInicio", fechaInicio); 
        model.addAttribute("fechaFin", fechaFin);     

        return "reportes"; // -> Llama a reportes.html
    }

    // Para CU10: Cierre de Caja
    @GetMapping("/cierre-caja")
    public String mostrarCierreDeCaja(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            Model model) {

        if (fecha == null) {
            fecha = LocalDate.now();
        }

        CierreCajaDTO cierre = ventaService.realizarCierreCaja(fecha);
        model.addAttribute("cierre", cierre);
        model.addAttribute("fecha", fecha); 

        return "cierre-caja"; // -> Llama a cierre-caja.html
    }
}
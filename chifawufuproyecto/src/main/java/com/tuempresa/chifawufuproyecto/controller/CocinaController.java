package com.tuempresa.chifawufuproyecto.controller;

import com.tuempresa.chifawufuproyecto.model.Venta;
import com.tuempresa.chifawufuproyecto.service.CocinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class CocinaController {

    @Autowired
    private CocinaService cocinaService;

    /**
     * Muestra la página principal de la cocina con los pedidos pendientes.
     */
    @GetMapping("/cocina")
    public String mostrarPanelDeCocina(Model model) {
        List<Venta> pedidosPendientes = cocinaService.listarPedidosPendientes();
        model.addAttribute("pedidosPendientes", pedidosPendientes);
        return "cocina"; // -> Llama a cocina.html
    }

    /**
     * Marca un pedido como "LISTO"
     */
    @GetMapping("/cocina/marcar-listo/{id}")
    public String marcarPedidoComoListo(@PathVariable Long id) {
        cocinaService.actualizarEstadoPedido(id, "LISTO");
        return "redirect:/cocina";
    }
    
    /**
     * (Opcional) Marca un pedido de vuelta a "PENDIENTE"
     */
    @GetMapping("/cocina/marcar-pendiente/{id}")
    public String marcarPedidoComoPendiente(@PathVariable Long id) {
        cocinaService.actualizarEstadoPedido(id, "PENDIENTE");
        return "redirect:/cocina";
    }
}
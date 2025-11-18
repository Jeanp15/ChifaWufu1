package com.tuempresa.chifawufuproyecto.repository;

import com.tuempresa.chifawufuproyecto.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {
    
    // Método para Reportes
    List<Venta> findByFechaBetween(LocalDateTime inicio, LocalDateTime fin);

    // --- MÉTODO NUEVO AÑADIDO ---
    // Método para la Cocina (busca por estado y ordena por fecha)
    List<Venta> findByEstadoOrderByFechaAsc(String estado);
}
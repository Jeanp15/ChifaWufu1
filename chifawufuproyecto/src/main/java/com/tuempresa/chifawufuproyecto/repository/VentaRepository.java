package com.tuempresa.chifawufuproyecto.repository;

import com.tuempresa.chifawufuproyecto.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime; // <-- Añade esta importación
import java.util.List;          // <-- Añade esta importación

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {
    
    // --- MÉTODO NUEVO AÑADIDO ---
    /**
     * Busca todas las ventas que ocurrieron entre dos momentos (fecha y hora).
     * Spring Data JPA escribirá el SQL: "SELECT * FROM venta WHERE fecha BETWEEN ? AND ?"
     */
    List<Venta> findByFechaBetween(LocalDateTime inicio, LocalDateTime fin);
}
package com.tuempresa.chifawufuproyecto.repository;

import com.tuempresa.chifawufuproyecto.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {
    // Aquí podríamos añadir búsquedas por fecha para los reportes
}
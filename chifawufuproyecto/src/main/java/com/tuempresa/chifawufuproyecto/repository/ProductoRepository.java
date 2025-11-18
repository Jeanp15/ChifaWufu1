package com.tuempresa.chifawufuproyecto.repository;

import com.tuempresa.chifawufuproyecto.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List; // <-- Añade esta importación

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // NUEVO MÉTODO: Busca productos que están activos Y tienen stock > 0
    List<Producto> findByActivoTrueAndStockGreaterThan(Integer stock);
}
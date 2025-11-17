package com.tuempresa.chifawufuproyecto.repository;

import com.tuempresa.chifawufuproyecto.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    // JpaRepository<Producto, Long>
    // 1. "Producto": Es la entidad (Modelo) que manejará.
    // 2. "Long": Es el tipo de dato de la clave primaria (@Id) de Producto (el idProducto).

    // Por ahora no necesitamos métodos extra.
    // Spring nos da save(), findById(), findAll(), etc., gratis.
}
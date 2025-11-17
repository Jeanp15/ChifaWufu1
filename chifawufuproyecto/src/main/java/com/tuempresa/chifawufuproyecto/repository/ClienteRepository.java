package com.tuempresa.chifawufuproyecto.repository;

import com.tuempresa.chifawufuproyecto.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    // Aquí podríamos añadir búsquedas por DNI/RUC más adelante
}
package com.tuempresa.chifawufuproyecto.service;

import com.tuempresa.chifawufuproyecto.model.Cliente;
import com.tuempresa.chifawufuproyecto.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> listarTodosLosClientes() {
        return clienteRepository.findAll();
    }

    public void guardarCliente(Cliente cliente) {
        clienteRepository.save(cliente);
    }

    public Optional<Cliente> buscarClientePorId(Long id) {
        return clienteRepository.findById(id);
    }

    public void eliminarClientePorId(Long id) {
        clienteRepository.deleteById(id);
    }
}
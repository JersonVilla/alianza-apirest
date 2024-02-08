package com.springboot.alianza.apirest.services;

import java.util.List;

import com.springboot.alianza.apirest.models.entity.Cliente;

public interface IClienteService {

    public List<Cliente> findAll();

    public Cliente save(Cliente cliente);

    public Cliente findById(Long id);

    public Cliente saveCurrentClient(Cliente cliente, Long id);

}

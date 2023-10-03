package com.springboot.alianza.apirest.models.services;

import java.util.List;

import com.springboot.alianza.apirest.models.entity.Cliente;

public interface IClienteService {

	public List<Cliente> findAll();
	
	public Cliente save(Cliente cliente);
	
	public Cliente findById(Long id);
	
}

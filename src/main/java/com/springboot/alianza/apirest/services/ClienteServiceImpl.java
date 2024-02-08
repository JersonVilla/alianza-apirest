package com.springboot.alianza.apirest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.springboot.alianza.apirest.models.repository.IClienteRepository;

import lombok.extern.slf4j.Slf4j;

import com.springboot.alianza.apirest.models.entity.Cliente;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
public class ClienteServiceImpl implements IClienteService {

	@Autowired
	private IClienteRepository clienteRepository;

	@Override
	@Transactional(readOnly = true)
	public List<Cliente> findAll() {
		log.info("Llamando al método findAll()");
		return (List<Cliente>) clienteRepository.findAll();
	}

	@Override
	@Transactional
	public Cliente save(Cliente cliente) {
		log.info("Guardando cliente: {}", cliente);
		return clienteRepository.save(cliente);
	}

	@Override
	@Transactional(readOnly = true)
	public Cliente findById(Long id) {
		log.info("Buscando cliente por ID: {}", id);
		return clienteRepository.findById(id).orElse(null);
	}

	@Override
	public Cliente saveCurrentClient(Cliente cliente, Long id) {
		Cliente clienteActual = this.findById(id);

		if (clienteActual == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado");
		}

		try {
			clienteActual.setSharedKey(cliente.getSharedKey());
			clienteActual.setBusinessId(cliente.getBusinessId());
			clienteActual.setEmail(cliente.getEmail());
			clienteActual.setPhone(cliente.getPhone());
			clienteActual.setDataAdd(cliente.getDataAdd());

			Cliente clienteUpdated = this.save(clienteActual);
			log.info("Cliente actualizado correctamente: {}", clienteUpdated);

		} catch (DataAccessException e) {
			log.error("Error al actualizar el cliente en la base de datos", e);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Error al actualizar el cliente en la base de datos");
		}

		return clienteActual;
	}

}

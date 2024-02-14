package com.springboot.alianza.apirest.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.springboot.alianza.apirest.dto.ClienteDto;
import com.springboot.alianza.apirest.dto.GeneralResponse;
import com.springboot.alianza.apirest.dto.response.ClienteResponseDto;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import com.springboot.alianza.apirest.models.entity.Cliente;
import com.springboot.alianza.apirest.services.IClienteService;

@SpringBootTest
public class ClienteRestControllerTest {
	@Mock
	IClienteService clienteService;
	@InjectMocks
	ClienteRestController clienteRestController;

	static final Long ID = 1L;
	List<ClienteDto> clientes = new ArrayList<>();
	ClienteResponseDto clienteResponseDto = new ClienteResponseDto();

	@Test
	void findAllCustomer() {
		clientes.add(new ClienteDto(1L, "jvillamizar", "Jerson Villamizar", "jvillamizar@gmail.com", "31546507624", new Date()));
		clientes.add(new ClienteDto(2L, "ogarcia", "Oscar Garcia", "ogarcia@gmail.com", "3154650762", new Date()));

		clienteResponseDto.setClientes(clientes);

		when(clienteService.findAllCustomer())
				.thenReturn(new GeneralResponse<>(HttpStatus.OK, "OK", ClienteResponseDto.builder().clientes(clientes).build()));

		GeneralResponse<ClienteResponseDto> response = clienteRestController.findAllCustomer();

		assertNotNull(response);
		assertNotNull(response.getData());

		List<ClienteDto> clientesEnRespuesta = response.getData().getClientes();
		assertNotNull(clientesEnRespuesta);
		assertEquals(2, clientesEnRespuesta.size());
	}
	@Test
	void findCustomerById() throws Exception {
		when(clienteService.findCustomerById(ID))
				.thenReturn(new GeneralResponse<>(HttpStatus.OK, "OK", ClienteResponseDto.builder().clientes(clientes).build()));

		GeneralResponse<ClienteResponseDto> response = clienteRestController.findCustomerById(ID);

		assertEquals(HttpStatus.OK, response.getHttpStatus());
	}
	@Test
	void saveCustomerTest() throws Exception {

		when(clienteService.saveCustomer(new Cliente()))
				.thenReturn(new GeneralResponse<>(HttpStatus.OK, "OK", new ClienteDto()));

		GeneralResponse<ClienteDto> response = clienteRestController.saveCustomer(new Cliente());

		assertEquals(HttpStatus.OK, response.getHttpStatus());
	}

	@Test
	void updateCustomer() throws Exception{
		when(clienteService.updateCustomer(new Cliente(), ID))
				.thenReturn(new GeneralResponse<>(HttpStatus.OK, "OK", new ClienteDto()));

		GeneralResponse<ClienteDto> response = clienteRestController.updateCustomer(new Cliente(), ID);

		assertEquals(HttpStatus.OK, response.getHttpStatus());
	}
	
}

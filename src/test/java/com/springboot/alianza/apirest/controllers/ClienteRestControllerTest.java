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
	@Test
	void findAllCustomer() {
		List<ClienteDto> clientes = new ArrayList<>();
		clientes.add(new ClienteDto(1L, "jvillamizar", "Jerson Villamizar", "jvillamizar@gmail.com", "31546507624", new Date()));
		clientes.add(new ClienteDto(2L, "ogarcia", "Oscar Garcia", "ogarcia@gmail.com", "3154650762", new Date()));
		ClienteResponseDto clienteResponseDto = new ClienteResponseDto();
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

		Long Id = 1L;
		List<ClienteDto> clientes = new ArrayList<>();

		when(clienteService.findCustomerById(1L))
				.thenReturn(new GeneralResponse<>(HttpStatus.OK, "OK", ClienteResponseDto.builder().clientes(clientes).build()));

		GeneralResponse<ClienteResponseDto> response = clienteRestController.findCustomerById(Id);

		assertEquals(HttpStatus.OK, response.getHttpStatus());
	}
	@Test
	void saveCustomerTest() throws Exception {

		when(clienteService.saveCustomer(new Cliente())).thenReturn(
				new GeneralResponse<>(HttpStatus.OK, "OK", new ClienteDto()));

		GeneralResponse<ClienteDto> response = clienteRestController.saveCustomer(new Cliente());

		assertEquals(HttpStatus.OK, response.getHttpStatus());
	}

	@Test
	void updateCustomer() throws Exception{
		Long Id = 1L;
		when(clienteService.updateCustomer(new Cliente(), Id)).thenReturn(
				new GeneralResponse<>(HttpStatus.OK, "OK", new ClienteDto()));

		GeneralResponse<ClienteDto> response = clienteRestController.updateCustomer(new Cliente(), Id);

		assertEquals(HttpStatus.OK, response.getHttpStatus());
	}
	
}

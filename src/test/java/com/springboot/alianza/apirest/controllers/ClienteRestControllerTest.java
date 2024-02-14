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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.alianza.apirest.models.entity.Cliente;
import com.springboot.alianza.apirest.services.IClienteService;

@SpringBootTest
@AutoConfigureMockMvc
@WebAppConfiguration
public class ClienteRestControllerTest {

	private final static String URL = "/api/clientes";

	@Autowired
	MockMvc mockMvc;

	@Mock
	IClienteService clienteService;

	@InjectMocks
	ClienteRestController clienteRestController;

	@Test
	void findAllCustomer() {
		List<ClienteDto> clientes = new ArrayList<>();
		clientes.add(new ClienteDto(1L, "jvillamizar", "Jerson Villamizar", "jvillamizar@gmail.com", "31546507624", new Date()));
		clientes.add(new ClienteDto(2L, "ogarcia", "Oscar Garcia", "ogarcia@gmail.com", "3154650762", new Date()));

		when(clienteService.findAllCustomer()).thenReturn(new GeneralResponse<ClienteResponseDto>(
				ClienteResponseDto.builder().clientes(clientes).build(), HttpStatus.OK.getReasonPhrase()));

		// Llamar al método del controlador que queremos probar
		GeneralResponse<ClienteResponseDto> response = clienteRestController.findAllCustomer();

		// Verificar que la respuesta no sea nula
		assertNotNull(response);

		// Verificar que el campo data no sea nulo
		assertNotNull(response.getData());

		// Verificar que la lista de clientes no sea nula y tenga el tamaño esperado
		List<ClienteDto> clientesEnRespuesta = response.getData().getClientes();
		assertNotNull(clientesEnRespuesta);
		assertEquals(2, clientesEnRespuesta.size());
	}


	@Test
	void findCustomerById() throws Exception {

		List<ClienteDto> clientes = new ArrayList<>();
		clientes.add(new ClienteDto(1L, "jvillamizar", "Jerson Villamizar", "jvillamizar@gmail.com", "31546507624", new Date()));
		ClienteResponseDto clienteResponseDto = new ClienteResponseDto();
		clienteResponseDto.setClientes(clientes);

		when(clienteService.findCustomerById(1L))
				.thenReturn(new GeneralResponse<>(HttpStatus.OK, "OK", ClienteResponseDto.builder().clientes(clientes).build()));


		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(URL)
						.accept(MediaType.APPLICATION_JSON_VALUE)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(mapToJson(clientes)))
				.andReturn();

		assertEquals(200, result.getResponse().getStatus());
	}

	@Test
	void saveCustomer() throws Exception{
		Cliente cliente = buildCliente();

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(URL)
						.accept(MediaType.APPLICATION_JSON_VALUE)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(mapToJson(cliente)))
				.andReturn();

		assertEquals(200, result.getResponse().getStatus());
	}

	@Test
	void updateCustomer() throws Exception{
		Cliente cliente = buildCliente();

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(URL.concat("/1"))
						.accept(MediaType.APPLICATION_JSON_VALUE)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(mapToJson(cliente)))
				.andReturn();

		assertEquals(200, result.getResponse().getStatus());
	}

	private Cliente buildCliente() {
		Cliente cliente = new Cliente();
		cliente.setId(1L);
		cliente.setSharedKey("ogarcia");
		cliente.setBusinessId("Oscar Garcia");
		cliente.setEmail("ogarcia@gmail.com");
		cliente.setPhone("3154650761");
		cliente.setDataAdd(new Date());

		return cliente;
	}

	private String mapToJson(Object object) throws JsonProcessingException{
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(object);
	}
	
}

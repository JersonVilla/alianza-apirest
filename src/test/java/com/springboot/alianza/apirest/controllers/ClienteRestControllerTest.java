package com.springboot.alianza.apirest.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.alianza.apirest.models.entity.Cliente;
import com.springboot.alianza.apirest.models.services.IClienteService;

@SpringBootTest
@WebAppConfiguration
@AutoConfigureMockMvc
public class ClienteRestControllerTest {

	private final static String URL = "/api/clientes";
	@Autowired
	MockMvc mockMvc;
	
	@Mock
	IClienteService clienteService;
	@InjectMocks
	ClienteRestController clienteRestController;
	
	@Test
	void index() {
		List<Cliente> clientes = new ArrayList<Cliente>();
		clientes.add(new Cliente(1L,"jvillamizar","Jerson Villamizar","jvillamizar@gmail.com","31546507624",new Date()));
		clientes.add(new Cliente(2L,"ogarcia","Oscar Garcia","ogarcia@gmail.com","3154650762",new Date()));
		
		when(clienteService.findAll()).thenReturn(clientes);
		
		assertEquals(2, clienteRestController.index().size());
	}
	
	@Test
	void show() throws Exception {
		
		Cliente cliente = new Cliente(1L,"jvillamizar","Jerson Villamizar","jvillamizar@gmail.com","31546507624",new Date());
		
		when(clienteService.findById(1L)).thenReturn(cliente);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URL.concat("/1"))
				.accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		/**
		String expectedJson = this.mapToJson(cliente);
		String outputInJson = result.getResponse().getContentAsString().toString();
		
		assertThat(outputInJson).isEqualTo(expectedJson);
		*/
		
		assertEquals(200, result.getResponse().getStatus());
		
	}
	
	@Test
	void create() throws Exception{
		Cliente cliente = buildCliente();
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(URL)
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(mapToJson(cliente)))
				.andReturn();
		
		assertEquals(201, result.getResponse().getStatus());
	}
	
	@Test
	void update() throws Exception{
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

package com.springboot.alianza.apirest.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.server.ResponseStatusException;

import com.springboot.alianza.apirest.models.entity.Cliente;
import com.springboot.alianza.apirest.models.repository.IClienteRepository;

@SpringBootTest
@WebAppConfiguration
@AutoConfigureMockMvc
public class ClienteServiceImplTest {

	@Mock
	private IClienteRepository clienteRepository;
	
	@InjectMocks
    private ClienteServiceImpl clienteService;
	
	private Cliente cliente;

    @BeforeEach
    public void setUp() {
        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setSharedKey("jvillamizar");
        cliente.setBusinessId("jerson villamizar");
        cliente.setEmail("ing.jersonvilla@gmail.com");
        cliente.setPhone("3154650762");
        cliente.setDataAdd(new Date());
    }
	
	@Test
    public void findAll() {
        Cliente cliente1 = new Cliente();
        cliente1.setId(1L);
        Cliente cliente2 = new Cliente();
        cliente2.setId(2L);
        List<Cliente> clientes = Arrays.asList(cliente1, cliente2);
        
        when(clienteRepository.findAll()).thenReturn(clientes);
        
        List<Cliente> resultado = clienteService.findAll();

        // Then
        assertThat(resultado).isNotNull();
        assertThat(resultado.size()).isEqualTo(2);
        assertThat(resultado.get(0).getId()).isEqualTo(1L);
        assertThat(resultado.get(1).getId()).isEqualTo(2L);
    }
	
	@Test
    public void save() {
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);
        
        Cliente resultado = clienteService.save(cliente);
        
        assertThat(resultado).isEqualTo(cliente);
    }
	
	
	@Test
    public void findById() {
        Long id = 1L;
        when(clienteRepository.findById(id)).thenReturn(java.util.Optional.of(cliente));
        
        Cliente clienteEncontrado = clienteService.findById(id);
        
        assertEquals(id, clienteEncontrado.getId());
    }

    @Test
    public void findByIdNotFound() {
        Long id = 2L;
        when(clienteRepository.findById(id)).thenReturn(Optional.empty());
        
        Cliente clienteEncontrado = clienteService.findById(id);
        
        assertEquals(null, clienteEncontrado);
    }
    
    @Test
    public void saveCurrentClient() {
        Long clientId = 1L;
        when(clienteRepository.findById(clientId)).thenReturn(Optional.of(cliente));
        
        Cliente clienteNuevo = new Cliente();
        clienteNuevo.setSharedKey("alianza");
        clienteNuevo.setBusinessId("alianza");
        clienteNuevo.setEmail("alianza@gmail.com");
        clienteNuevo.setPhone("3219876543");
        clienteNuevo.setDataAdd(new Date());
        
        when(clienteRepository.save(cliente)).thenReturn(cliente);
        
        Cliente clienteActualizado = clienteService.saveCurrentClient(clienteNuevo, clientId);
        
        assertEquals(cliente.getId(), clienteActualizado.getId());
        assertEquals(clienteNuevo.getSharedKey(), clienteActualizado.getSharedKey());
        assertEquals(clienteNuevo.getBusinessId(), clienteActualizado.getBusinessId());
        assertEquals(clienteNuevo.getEmail(), clienteActualizado.getEmail());
        assertEquals(clienteNuevo.getPhone(), clienteActualizado.getPhone());
        assertEquals(clienteNuevo.getDataAdd(), clienteActualizado.getDataAdd());
    }

    @Test
    public void saveCurrentClientNotFound() {
        Long clientId = 1L;
        when(clienteRepository.findById(clientId)).thenReturn(Optional.empty());
        
        assertThrows(ResponseStatusException.class,
            () -> clienteService.saveCurrentClient(new Cliente(), clientId));
    }
	
}

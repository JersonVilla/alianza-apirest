package com.springboot.alianza.apirest.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.springboot.alianza.apirest.dto.ClienteDto;
import com.springboot.alianza.apirest.dto.GeneralResponse;
import com.springboot.alianza.apirest.dto.response.ClienteResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.web.WebAppConfiguration;

import com.springboot.alianza.apirest.models.entity.Cliente;
import com.springboot.alianza.apirest.models.repository.IClienteRepository;

@SpringBootTest
@WebAppConfiguration
@AutoConfigureMockMvc
public class ClienteServiceImplTest {

    @Mock
    private IClienteRepository clienteRepository;

    @Mock
    private ModelMapper mapper;

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
    void findAllCustomerTest() {
        List<Cliente> clientes = Arrays.asList(new Cliente(), new Cliente());
        when(clienteRepository.findAll()).thenReturn(clientes);

        GeneralResponse<ClienteResponseDto> response = clienteService.findAllCustomer();

        assertThat(response.getData()).isNotNull();
        assertThat(response.getData().getClientes()).hasSize(2);
    }

    @Test
    void saveCustomerTest() {
        Cliente cliente = new Cliente();
        when(clienteRepository.save(cliente)).thenReturn(cliente);

        GeneralResponse<ClienteDto> response = clienteService.saveCustomer(cliente);

        assertThat(response).isNotNull();
        assertThat(response.getHttpStatus()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void findCustomerByIdTest() {
        Long id = 1L;
        Cliente cliente = new Cliente();
        cliente.setId(id);
        when(clienteRepository.findById(id)).thenReturn(Optional.of(cliente));

        GeneralResponse<ClienteResponseDto> response = clienteService.findCustomerById(id);

        assertThat(response.getData()).isNotNull();
        assertThat(response.getData().getClientes()).hasSize(1);
    }


    @Test
    void updateCustomerTest() {
        // Given
        Long id = 1L;
        Cliente cliente = new Cliente();
        cliente.setId(id);
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setId(id);
        when(clienteRepository.findById(id)).thenReturn(Optional.of(cliente));
        when(mapper.map(cliente, ClienteDto.class)).thenReturn(clienteDto);
        when(clienteRepository.save(cliente)).thenReturn(cliente);

        // When
        GeneralResponse<ClienteDto> response = clienteService.updateCustomer(cliente, id);

        // Then
        assertThat(response.getData()).isNotNull();
        assertThat(response.getHttpStatus()).isEqualTo(HttpStatus.OK);
    }
	
}

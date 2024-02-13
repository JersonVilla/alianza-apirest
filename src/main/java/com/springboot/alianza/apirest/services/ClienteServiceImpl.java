package com.springboot.alianza.apirest.services;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.springboot.alianza.apirest.dto.ClienteDto;
import com.springboot.alianza.apirest.dto.GeneralResponse;
import com.springboot.alianza.apirest.dto.response.ClienteResponseDto;
import com.springboot.alianza.apirest.exception.GeneralException;
import com.springboot.alianza.apirest.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.springboot.alianza.apirest.models.repository.IClienteRepository;

import lombok.extern.slf4j.Slf4j;

import com.springboot.alianza.apirest.models.entity.Cliente;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements IClienteService {

    private final IClienteRepository clienteRepository;
    private final ModelMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public GeneralResponse<ClienteResponseDto> findAll() {
        log.info("Llamando al método findAll()");
        ClienteResponseDto clienteResponseDto = new ClienteResponseDto(clienteRepository.findAll().stream()
                .map(cliente -> mapper.map(cliente, ClienteDto.class)).collect(Collectors.toList()));
        return new GeneralResponse<>(clienteResponseDto, HttpStatus.OK.getReasonPhrase());
    }

    @Override
    @Transactional
    public GeneralResponse<ClienteDto> save(Cliente cliente) {
        log.info("Guardando cliente: {}", cliente);
        Cliente clienteGuardado = clienteRepository.save(cliente);
        ClienteDto clienteDto = mapper.map(clienteGuardado, ClienteDto.class);
        return new GeneralResponse<>(clienteDto, HttpStatus.CREATED.getReasonPhrase());
    }

    @Override
    public GeneralResponse<ClienteResponseDto> findById(Long id) {
        log.info("Buscando cliente por ID: {}", id);
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado"));

        ClienteDto clienteDto = mapper.map(cliente, ClienteDto.class);
        ClienteResponseDto clienteResponseDto = new ClienteResponseDto(Collections.singletonList(clienteDto));

        return new GeneralResponse<>(clienteResponseDto, HttpStatus.OK.getReasonPhrase());
    }

    @Override
    public GeneralResponse<ClienteDto> saveCurrentClient(Cliente cliente, Long id) {
        log.info("Actualizando cliente con ID: {}", id);

        try {
            log.info("Buscar el cliente por su ID");
            GeneralResponse<ClienteResponseDto> clienteResponse = findById(id);
            ClienteResponseDto clienteResponseDto = clienteResponse.getData();

            log.info("Verificar si se encontró el cliente");
            if (clienteResponseDto == null || clienteResponseDto.getClientes().isEmpty()) {
                throw new NotFoundException("Cliente no encontrado");
            }

            log.info("Actualizar los datos del cliente");
            ClienteDto clienteDto = clienteResponseDto.getClientes().get(0);
            clienteDto.setSharedKey(cliente.getSharedKey());
            clienteDto.setBusinessId(cliente.getBusinessId());
            clienteDto.setEmail(cliente.getEmail());
            clienteDto.setPhone(cliente.getPhone());
            clienteDto.setDataAdd(cliente.getDataAdd());

            GeneralResponse<ClienteDto> clienteUpdated = save(mapper.map(clienteDto, Cliente.class));
            log.info("Cliente actualizado correctamente: {}", clienteUpdated);

            return new GeneralResponse<>(clienteDto, HttpStatus.CREATED.getReasonPhrase());

        } catch (DataAccessException e) {
            log.error("Error al actualizar el cliente en la base de datos", e);
            throw new GeneralException("Error al actualizar el cliente en la base de datos");
        }
    }


    @Override
    public GeneralResponse<ClienteResponseDto> searchClientsSharedKey(String sharedKey) {
        log.info("Llamando al método findBySharedKeyContainingIgnoreCase()");
        List<Cliente> clientes = clienteRepository.findBySharedKeyContainingIgnoreCase(sharedKey);

        List<ClienteDto> clienteDtos = clientes.stream().map(cliente -> mapper.map(cliente, ClienteDto.class))
                .collect(Collectors.toList());

        ClienteResponseDto clienteResponseDto = new ClienteResponseDto(clienteDtos);
        return new GeneralResponse<>(clienteResponseDto, HttpStatus.OK.getReasonPhrase());
    }

}

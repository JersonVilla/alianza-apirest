package com.springboot.alianza.apirest.services;

import com.springboot.alianza.apirest.dto.ClienteDto;
import com.springboot.alianza.apirest.dto.GeneralResponse;
import com.springboot.alianza.apirest.dto.response.ClienteResponseDto;
import com.springboot.alianza.apirest.models.entity.Cliente;

public interface IClienteService {

    public GeneralResponse<ClienteResponseDto> findAll();

    public GeneralResponse<ClienteDto> save(Cliente cliente);

    public GeneralResponse<ClienteResponseDto> findById(Long id);

    public GeneralResponse<ClienteDto> saveCurrentClient(Cliente cliente, Long id);

    public GeneralResponse<ClienteResponseDto> searchClientsSharedKey(String sharedKey);

}

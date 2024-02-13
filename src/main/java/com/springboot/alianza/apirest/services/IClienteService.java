package com.springboot.alianza.apirest.services;

import com.springboot.alianza.apirest.dto.ClienteDto;
import com.springboot.alianza.apirest.dto.GeneralResponse;
import com.springboot.alianza.apirest.dto.response.ClienteResponseDto;
import com.springboot.alianza.apirest.models.entity.Cliente;

public interface IClienteService {

    public GeneralResponse<ClienteResponseDto> findAllCustomer();

    public GeneralResponse<ClienteDto> saveCustomer(Cliente cliente);

    public GeneralResponse<ClienteResponseDto> findCustomerById(Long id);

    public GeneralResponse<ClienteDto> updateCustomer(Cliente cliente, Long id);

    public GeneralResponse<ClienteResponseDto> searchCustomerBySharedKey(String sharedKey);

}

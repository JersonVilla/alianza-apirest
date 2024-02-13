package com.springboot.alianza.apirest.dto.response;

import com.springboot.alianza.apirest.dto.ClienteDto;
import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteResponseDto implements Serializable {

    private List<ClienteDto> clientes;

    private static final long serialVersionUID = -7705760786893305910L;

}
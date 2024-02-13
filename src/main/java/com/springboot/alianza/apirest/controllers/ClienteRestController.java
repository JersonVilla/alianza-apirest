package com.springboot.alianza.apirest.controllers;

import java.io.IOException;

import com.springboot.alianza.apirest.dto.ClienteDto;
import com.springboot.alianza.apirest.dto.GeneralResponse;
import com.springboot.alianza.apirest.dto.response.ClienteResponseDto;
import com.springboot.alianza.apirest.utilities.Constantes;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.springboot.alianza.apirest.models.entity.Cliente;
import com.springboot.alianza.apirest.services.IClienteService;
import com.springboot.alianza.apirest.utilities.ExcelUtil;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class ClienteRestController {

    private final IClienteService clienteService;


    @GetMapping("/clientes")
    public GeneralResponse<ClienteResponseDto> index() {

        log.info(Constantes.FIND_ALL);
        return clienteService.findAll();

    }

    @GetMapping("/clientes/{id}")
    public GeneralResponse<ClienteResponseDto> show(@PathVariable Long id) {

        log.info(Constantes.FIND_BY_ID, id);
        return clienteService.findById(id);

    }


    @PostMapping("/clientes")
    public GeneralResponse<ClienteDto> create(@Valid @RequestBody Cliente cliente) {

        log.info(Constantes.SAVE_CLIENT, cliente.getBusinessId());
        return clienteService.save(cliente);

    }

    @PutMapping("/clientes/{id}")
    public GeneralResponse<ClienteDto> update(@RequestBody Cliente cliente, @PathVariable Long id) {

        log.info(Constantes.UPDATE_CLIENT, id);
        return clienteService.saveCurrentClient(cliente, id);

    }


    @GetMapping("/clientes/buscar")
    public GeneralResponse<ClienteResponseDto> buscarPorSharedKey(@RequestParam("sharedKey") String sharedKey) {

        log.info(Constantes.SHARED_KEY);
        return clienteService.searchClientsSharedKey(sharedKey);

    }


    @GetMapping("/clientes/export")
    public void exportToCSV(HttpServletResponse response) throws IOException {

        log.info(Constantes.EXPORT_CSV);

        GeneralResponse<ClienteResponseDto> clients = clienteService.findAll();
        ExcelUtil.exportToExcel(response, clients.getData().getClientes());

        log.info(Constantes.EXPORT_CSV_COMPLETE);

    }

}

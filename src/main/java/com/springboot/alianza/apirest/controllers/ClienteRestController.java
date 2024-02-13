package com.springboot.alianza.apirest.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.springboot.alianza.apirest.dto.ClienteDto;
import com.springboot.alianza.apirest.dto.GeneralResponse;
import com.springboot.alianza.apirest.dto.response.ClienteResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

        log.info("Iniciando método index() para obtener la lista de clientes");
        return clienteService.findAll();

    }

    @GetMapping("/clientes/{id}")
    public GeneralResponse<ClienteResponseDto> show(@PathVariable Long id) {

        log.info("Recuperando cliente con ID: {}", id);
        return clienteService.findById(id);

    }


    @PostMapping("/clientes")
    public GeneralResponse<ClienteDto> create(@Valid @RequestBody Cliente cliente) {

        log.info("Cliente creado con éxito: {}", cliente.getId());
        return clienteService.save(cliente);

    }

    @PutMapping("/clientes/{id}")
    public GeneralResponse<ClienteDto> update(@RequestBody Cliente cliente, @PathVariable Long id) {

        log.info("Actualizando cliente con ID: {}", id);
        return clienteService.saveCurrentClient(cliente, id);

    }


    @GetMapping("/clientes/buscar")
    public GeneralResponse<ClienteResponseDto> buscarPorSharedKey(@RequestParam("sharedKey") String sharedKey) {

        log.info("Iniciando búsqueda por sharedKey");
        return clienteService.searchClientsSharedKey(sharedKey);

    }


    @GetMapping("/clientes/export")
    public void exportToCSV(HttpServletResponse response) throws IOException {

        log.info("Iniciando exportación de clientes a CSV");

        GeneralResponse<ClienteResponseDto> clients = clienteService.findAll();
        ExcelUtil.exportToExcel(response, clients.getData().getClientes());

        log.info("Exportación de clientes a CSV completada");

    }

}

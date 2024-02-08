package com.springboot.alianza.apirest.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class ClienteRestController {

    @Autowired
    private IClienteService clienteService;

    
    @GetMapping("/clientes")
    public List<Cliente> index() {
    	log.info("Iniciando método index() para obtener la lista de clientes");
        return clienteService.findAll();
    }

    @GetMapping("/clientes/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {

    	log.info("Recuperando cliente con ID: {}", id);
        Map<String, Object> response = new HashMap<>();
        Cliente cliente = null;

        try {
            cliente = clienteService.findById(id);
        } catch (DataAccessException e) {
        	log.error("Error al recuperar cliente con ID {}: {}", id, e.getMessage());
            response.put("mensaje", "Error al realizar la consulta en BD");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        if (cliente == null) {
        	log.warn("Cliente con ID {} no encontrado", id);
            response.put("mensaje", "El cliente id: ".concat(id.toString().concat(" no existe en BD")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        log.info("Cliente recuperado con éxito: {}", cliente);
        return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
    }

    @PostMapping("/clientes")
    public ResponseEntity<?> create(@Valid @RequestBody Cliente cliente) {

        Map<String, Object> response = new HashMap<>();
        Cliente clienteNuevo = null;

        try {
            clienteNuevo = clienteService.save(cliente);
            log.info("Cliente creado con éxito: {}", clienteNuevo.getId());
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar el insert en la BD");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            log.error("Error al crear el cliente: {}", e.getMessage(), e);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        response.put("mensaje", "El cliente se ha creado con exito");
        response.put("cliente", clienteNuevo);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @PutMapping("/clientes/{id}")
    public ResponseEntity<Cliente> update(@RequestBody Cliente cliente, @PathVariable Long id) {

    	log.info("Actualizando cliente con ID: {}", id);
        return new ResponseEntity<Cliente>(clienteService.saveCurrentClient(cliente, id), HttpStatus.CREATED);

    }
    
    @GetMapping("/clientes/buscar")
    public List<Cliente> buscarPorSharedKey(@RequestParam("sharedKey") String sharedKey) {
    	log.info("Iniciando busqueda por sharedKey");
        return clienteService.searchClientsSharedKey(sharedKey);
    }

    @GetMapping("/clientes/export")
    public void exportToCSV(HttpServletResponse response) throws IOException {
    	
    	log.info("Iniciando exportación de clientes a CSV");
    	
        List<Cliente> listClients = clienteService.findAll();
        ExcelUtil.exportToExcel(response, listClients);
        
        log.info("Exportación de clientes a CSV completada");
        
    }

}

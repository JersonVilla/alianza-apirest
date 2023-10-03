package com.springboot.alianza.apirest.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.alianza.apirest.models.entity.Cliente;

public interface IClienteDao extends JpaRepository<Cliente, Long>{

}

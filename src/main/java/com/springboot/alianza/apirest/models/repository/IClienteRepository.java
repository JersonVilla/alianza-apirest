package com.springboot.alianza.apirest.models.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.alianza.apirest.models.entity.Cliente;
import org.springframework.stereotype.Repository;

@Repository
public interface IClienteRepository extends JpaRepository<Cliente, Long>{
	
	List<Cliente> findBySharedKeyContainingIgnoreCase(String sharedKey);

}

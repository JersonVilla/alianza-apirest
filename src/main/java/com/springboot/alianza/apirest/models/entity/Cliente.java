package com.springboot.alianza.apirest.models.entity;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Data
@Entity
@Table(name = "clientes")
public class Cliente implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "shared_key")
	private String sharedKey;

	@Column(name = "business_id")
	private String businessId;

	private String email;
	private String phone;

	@Column(name = "data_add")
	@Temporal(TemporalType.DATE)
	private Date dataAdd;

	@PrePersist
	public void prePersist() {
		String[] parts = businessId.toLowerCase().split(" ");
		String part1 = parts[0];
		String part2 = parts[1];
		sharedKey = part1.charAt(0) + part2;
	}

	public Cliente() {
	}

	public Cliente(Long id, String sharedKey, String businessId, String email, String phone, Date dataAdd) {
		this.id = id;
		this.sharedKey = sharedKey;
		this.businessId = businessId;
		this.email = email;
		this.phone = phone;
		this.dataAdd = dataAdd;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}

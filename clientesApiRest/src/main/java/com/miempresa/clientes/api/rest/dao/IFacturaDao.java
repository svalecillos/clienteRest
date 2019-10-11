package com.miempresa.clientes.api.rest.dao;

import org.springframework.data.repository.CrudRepository;

import com.miempresa.clientes.api.rest.models.Factura;

public interface IFacturaDao extends CrudRepository<Factura, Long>{
	
}

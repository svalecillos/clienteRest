package com.miempresa.clientes.api.rest.dao;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.CrudRepository;

import com.miempresa.clientes.api.rest.models.Cliente;

public interface IClienteDao extends JpaRepository<Cliente, Long>{

}

package com.miempresa.clientes.api.rest.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.miempresa.clientes.api.rest.models.Cliente;
import com.miempresa.clientes.api.rest.models.Factura;
import com.miempresa.clientes.api.rest.models.Producto;
import com.miempresa.clientes.api.rest.models.Region;

public interface IClienteService {
	
	public List<Cliente> findAll();
	
	public Page<Cliente> findAll(Pageable pageable);
	
	public Cliente findById(Long id);
	
	public Cliente save(Cliente cliente);
	
	public void delete(Long id);
	
	public List<Region> findAllRegiones();
	
	public Factura findFacturaById(Long id);
	
	public Factura saveFactura(Factura factura);
	
	public void deleteFacturaById(Long id);
	
	public List<Producto> findProductoByNombre(String termino);
	
}

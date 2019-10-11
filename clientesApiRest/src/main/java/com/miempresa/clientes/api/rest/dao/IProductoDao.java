package com.miempresa.clientes.api.rest.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.miempresa.clientes.api.rest.models.Producto;

public interface IProductoDao extends CrudRepository<Producto, Long>{
	
	//PRIMERA OPCION
	//Una consulta JPA, con porcentaje al final buscara al comienzo de la cadena. Esto es un append
	@Query("select p from Producto p where p.nombre like %?1%")
	public List<Producto> findByNombre(String termino); 
	
	//SEGUNDA OPCION
	//Consulta por JPA de spring , ignora las mayusculas
	public List<Producto> findByNombreContainingIgnoreCase(String termino);
	
	//TERCERA OPCION
	public List<Producto> findByNombreStartingWithIgnoreCase(String termino);
	
}

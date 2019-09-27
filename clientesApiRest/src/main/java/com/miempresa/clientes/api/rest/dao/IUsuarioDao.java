package com.miempresa.clientes.api.rest.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.miempresa.clientes.api.rest.models.Usuario;

//Data Access Object
public interface IUsuarioDao extends CrudRepository<Usuario, Long>{
	
	//Se puede hacer una consulta atravez del nombre del metodo(Query method name)
	//Se ejecutaria la consulta HQL o JPQL
	//Select u from Usuario u where u.username=?
	public Usuario findByUsername(String username);
	
	//Segunda forma
	//Podemos escribir el nombre como queramos de la funcion.
	//La consulta se trae de la anotacion y no del nombre del metodo
	@Query("select u from Usuario u where u.username=?1")
	public Usuario buscarPorNombre(String username);
	
}

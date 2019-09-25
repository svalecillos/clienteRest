package com.miempresa.clientes.api.rest.services;

import com.miempresa.clientes.api.rest.models.Usuario;

public interface IUsuarioService {
	
	public Usuario findByUsername(String username);
	
}

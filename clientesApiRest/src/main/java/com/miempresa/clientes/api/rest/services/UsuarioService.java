package com.miempresa.clientes.api.rest.services;


import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.miempresa.clientes.api.rest.dao.IUsuarioDao;
import com.miempresa.clientes.api.rest.models.Usuario;

//UserDetailsService es una interfaz de spring security
@Service//Con esta anotacion la registramos como beans de spring en el contenedor
public class UsuarioService implements UserDetailsService{
	
	private Logger logger = LoggerFactory.getLogger(UsuarioService.class);
	
	@Autowired
	private IUsuarioDao usuarioDao;
	
	@Override
	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		//Obtener el usuario atravez de sus username
		Usuario usuario = usuarioDao.findByUsername(username);
		
		if(usuario == null) {
			logger.error("Error en el login: no existe el usuario en el sistema!");
			throw new UsernameNotFoundException("Error en el login: no existe el usuario en el sistema!");
		}
		
		List<GrantedAuthority> authorities = usuario.getRoles()
				.stream()
				.map(role -> new SimpleGrantedAuthority(role.getNombre()))//Por cada rol lo transformamos en un objeto grantedAuthority
				.peek(authority -> logger.info("Role: " + authority.getAuthority()))
				.collect(Collectors.toList());//Convertimos el strem en una coleccion
		
		return new User(usuario.getUsername(), usuario.getPassword(), usuario.getEnable(), true, true, true, authorities);
	}

}

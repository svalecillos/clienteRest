package com.miempresa.clientes.api.rest.auth;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter{
	
	//Metodos que permiten implementar todas las reglas de seguridad de nuestros endpoints,
	//de nuestras rutas hacia los regursos
	//Por ejemplo si se quiere dar permisos a todos al listado de clientes
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/clientes").permitAll()//Solamente ver el listado de clientes por get
		.anyRequest().authenticated();
	}
	 	
	
	
}

package com.miempresa.clientes.api.rest.auth;

import java.util.Arrays;

import javax.persistence.criteria.Order;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter{
	
	//Metodos que permiten implementar todas las reglas de seguridad de nuestros endpoints,
	//de nuestras rutas hacia los regursos
	//Por ejemplo si se quiere dar permisos a todos al listado de clientes
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/clientes", "/api/clientes/page/**", "/api/uploads/**","/images/**").permitAll()//Solamente ver el listado de clientes por get
		
		//ESTE PROCESO SE HARA EN EL CONTROLADOR USANDO LAS ANOTACIONES
		.anyRequest().authenticated()
		.and().cors().configurationSource(corsConfigurationSource());
	}
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		
		CorsConfiguration config = new CorsConfiguration();
		
		//PErmitir el dominio donde reside nuestra aplicacion cliente
		config.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
		
		//Configurar todos los metodos o verbos que vamos a permitir en nuestra aplicacion
		config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));//Se puede aplicar todo esto por un *
		config.setAllowCredentials(true);
		config.setAllowedHeaders(Arrays.asList("Content-type", "Authorization"));
		
		//Registramos esta configuracion del cors para todas nuestras rutas del backeng
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		
		return source;
	}
	
	/*Creamos un filtro de cords, le pasamos la configuracion del metodo corsConfigurationSource y 
	 * lo registramos dentro del stack del conjunto de filtros que maneja spring framework, y le dimos una prioridad alta.**/
	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilter(){
		
		FilterRegistrationBean<CorsFilter>	bean = new FilterRegistrationBean<CorsFilter>(new CorsFilter(corsConfigurationSource()));
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return bean;
		
	}
	 	
	
	
}

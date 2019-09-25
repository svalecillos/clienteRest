package com.miempresa.clientes.api.rest.auth;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

/**
 * Esta clase se encarga de todo el proceso de autenticacion por parte del auth
 * desde el proceso del login, crear el token, etc.
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter{
	
	@Autowired
	private BCryptPasswordEncoder  passwordEncoder;
	
	@Autowired
	private InfoAdicionalToken infoInfoAdicionalToken;
	
	@Autowired
	@Qualifier("authenticationManager")
	private AuthenticationManager authenticationManager;
	
	//IMplementamos tres metodos para la configuracion
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.tokenKeyAccess("permitAll()")
		.checkTokenAccess("isAuthenticated()");//Endpoint que verifica el token y su firma
	}
	
	//Configura los clientes que vas acceder al api rest
	@Override 
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory().withClient("angularapp")
		.secret(passwordEncoder.encode("12345"))
		.scopes("read", "write")//Alcanze, es el permiso de la aplicacion, de lectura y escritura
		.authorizedGrantTypes("password", "refresh_token")//Credenciales del usuario que va a iniciar session en el backeng
		.accessTokenValiditySeconds(3600)													//Con refresh token vamos a obtener un token renovado, sin volver a inicia sessiona
		.refreshTokenValiditySeconds(3600);
	}  
	
	//Se encarga de todo el proceso de autenticacion, genera y validar el token
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		//Agrega el token mas la informacion adicional del mismo. 
		tokenEnhancerChain.setTokenEnhancers(Arrays.asList(infoInfoAdicionalToken,accessTokenConverter()));
		
		endpoints.authenticationManager(authenticationManager)
		.accessTokenConverter(accessTokenConverter())//Es el encargado de manejar varias cosas relacionadas al token
		.tokenEnhancer(tokenEnhancerChain);
	}
	
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
		//Agregar clave secreta al JWT es propia
		jwtAccessTokenConverter.setSigningKey(JwtConfig.LLAVE_SECRETA);
		return jwtAccessTokenConverter;
	}
	
}

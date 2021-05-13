package com.edgleidson.cursomc.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.edgleidson.cursomc.security.JWTFiltroDeAutenticacao;
import com.edgleidson.cursomc.security.JWTFiltroDeAutorizacao;
import com.edgleidson.cursomc.security.JWTutil;

//Classe de Configuracao para Autenticacao.
//Herdar = (WebSecurityConfigurerAdapter) da framework Spring Security.

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SegurancaConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private Environment ambiente; 
	@Autowired
	private UserDetailsService userDetailsService; //Injetando da dependencia da Interface(UserDetailsService).
	@Autowired
	private JWTutil jwtUtil;

	// Vetor[] para definir quais os caminhos/URL, que por padrao estaram liberados.
	private static final String[] PUBLIC_MATCHERS = {
			"/h2-console/**" 
			};

	// Vetor[] para definir quais os caminhos/URL, que estaram liberados apenas para leitura/visualizacao (GET).
	private static final String[] PUBLIC_MATCHERS_GET = { 
			"/produtos/**", 
			"/categorias/**",
			"/estados/**"
			};

	// Vetor[] para definir quais os caminhos/URL, que estaram liberados para gravacao (POST).
	private static final String[] PUBLIC_MATCHERS_POST = { 
			"/clientes",
			"/autenticacao/esqueci/**"
			};
	
	// Sobreescrever o metodo(configure()) que veio da classe(WebSecurityConfigurerAdapter). 
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// Para liberar acesso via Banco de dados H2.
		if (Arrays.asList(ambiente.getActiveProfiles()).contains("test")) {
			http.headers().frameOptions().disable();
		}

		//Para ativar o @Bean(CorsConfigurationSource) & Desabilitar a protecao CSRF.
		http.cors().and().csrf().disable();

		http.authorizeRequests()
		.antMatchers(HttpMethod.POST,PUBLIC_MATCHERS_POST).permitAll()//Permitir o metodo(POST) para os caminhos que estiverem no vetor.
		.antMatchers(HttpMethod.GET,PUBLIC_MATCHERS_GET).permitAll()//So vai permitir o metodo(GET) para os caminhos que estiverem no vetor.
		.antMatchers(PUBLIC_MATCHERS).permitAll() //Todos os caminho que tiver nesse Vetor, sera permitido.
		.anyRequest().authenticated(); //Para o restante exigir uma autenticacao.

		// Filtro de autenticacao.
		http.addFilter(new JWTFiltroDeAutenticacao(authenticationManager(), jwtUtil));
		
		// Filtro de autorizacao.
		http.addFilter(new JWTFiltroDeAutorizacao(authenticationManager(), jwtUtil, userDetailsService));
		
		// Para assegurar que o Backend nao vai criar secao de Usuario.
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	
	//Sobreescrever o metodo(Configure()), para informar quem eh UserDetailsService e o algoritmo de codificacao da senha.
	 public void configure(AuthenticationManagerBuilder autenticacao) throws Exception{
		 autenticacao.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	 } 	
	
	 
	// Metodo @Bean para dar acesso basico de multiplas fontes para todos os caminhos.
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;
	}
	
	
	// Metodo @Bean para criptograr senha.
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}	
}
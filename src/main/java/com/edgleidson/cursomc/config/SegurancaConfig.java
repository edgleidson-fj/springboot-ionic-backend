package com.edgleidson.cursomc.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

//Classe de Configuração para Autenticação.
//Herdar = WebSecurityConfigurerAdapter.

@Configuration
@EnableWebSecurity
public class SegurancaConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private Environment ambiente; 

	// Vetor[] para definir quais os caminhos/URL, que por padrão estaram liberados.
	private static final String[] PUBLIC_MATCHERS = {
			"/h2-console/**" 
			};

	// Vetor[] para definir quais os caminhos/URL, que estaram liberados apenas para leitura/visualização.
	private static final String[] PUBLIC_MATCHERS_GET = { 
			"/produtos/**", 
			"/categorias/**" 
			};

	// Sobreescrver o método(configure()) que veio da classe(WebSecurityConfigurerAdapter). 
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// Para liberar acesso via Banco de dados H2.
		if (Arrays.asList(ambiente.getActiveProfiles()).contains("test")) {
			http.headers().frameOptions().disable();
		}

		//Para ativar o @Bean(CorsConfigurationSource) & Desabilitar a proteção CSRF.
		http.cors().and().csrf().disable();

		http.authorizeRequests()
		.antMatchers(HttpMethod.GET,PUBLIC_MATCHERS_GET).permitAll()//Só vai permitir o método(GET) para os caminhos que estiverem no vetor.
		.antMatchers(PUBLIC_MATCHERS).permitAll() //Todos os caminho que tiver nesse Vetor, será permitido.
		.anyRequest().authenticated(); //Para o restante exigir uma autenticação.

		// Para assegurar que o Backend não vai criar seção de Usuário.
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	
	
	// Método @Bean para dar acesso básico de multiplas fontes para todos os caminhos.
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;
	}
	
	// Método @Bean para criptograr senha.
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
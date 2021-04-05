package com.edgleidson.cursomc.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

//Classe de Filtro de Autorização.
//Herdar = (BasicAuthenticationFilter) da framework Spring Security.

public class JWTFiltroDeAutorizacao extends BasicAuthenticationFilter{
	
	private JWTutil jwtUtil;
	private UserDetailsService userDetailsService;

	// Construtor obrigatório do (BasicAuthenticationFilter).
	// Injetando as dependência (AuthenticationManager, JWTutil & UserDetailsService) através do Construtor.
	// AuthenticationManager já está incluso na SuperClasse.
	public JWTFiltroDeAutorizacao(AuthenticationManager authenticationManager, JWTutil jwtUtil, 
			UserDetailsService userDetailsService) {
		super(authenticationManager);
		this.jwtUtil = jwtUtil;
		this.userDetailsService = userDetailsService;
	}
	
	
	// Método padrão do (BasicAuthenticationFilter).
	// Método para interceptar a requisição e verificar se o usuário realmente está autorizado.
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		String cabecalho = request.getHeader("Autorizacao");
		
		if(cabecalho != null && cabecalho.startsWith("Usuario-Token ")) { //startsWith = Caracteres iniciais.
			UsernamePasswordAuthenticationToken autenticacaoToken = getAutenticacao(cabecalho.substring(14));			
			//Pegar o valor do Cabeçalho menos os 14 caracteres que estão no inicio(Usuario-Token_). 
			//substring = Descontar o número de caracteres iniciais.
			
			if(autenticacaoToken != null) {
				SecurityContextHolder.getContext().setAuthentication(autenticacaoToken);
				//Para liberar o acesso do filtro.
			}
		}
		chain.doFilter(request, response);
		//Continua a execução normalmente da requisição.
		
	}

	
	// Método getAutenticacao().
	private UsernamePasswordAuthenticationToken getAutenticacao(String token) {
		if(jwtUtil.tokenValido(token)) {
			String username = jwtUtil.getUsername(token); // Pegar o usuário(email) do Token.
			UserDetails user = userDetailsService.loadUserByUsername(username); // Carregar o usuário(email) no UserDetails.
			
			return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		}
		return null;
	}
}
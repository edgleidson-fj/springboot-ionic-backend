package com.edgleidson.cursomc.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.edgleidson.cursomc.dto.CredenciaisDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

//Classe de Filtro de Autenticação.
//Herdar = (UsernamePasswordAuthenticationFilter) da framework Spring Security.

public class JWTFiltroDeAutenticacao extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;
	private JWTutil jwtUtil;

	//Injetando as dependência (AuthenticationManager & JWTutil) através do Construtor.
	public JWTFiltroDeAutenticacao(AuthenticationManager authenticationManager, JWTutil jwtUtil) {
		setAuthenticationFailureHandler(new JWTAuthenticationFailureHandler());//Chamando do método.
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
	}

	// TENTAR AUTENTICAÇÃO.
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			//Pegar os dados que vem na Requesição e convertendo para o Tipo (CredenciaisDTO.class).
			CredenciaisDTO credenciaisDTO = new ObjectMapper().readValue(request.getInputStream(),CredenciaisDTO.class);

			//Instanciando um Objeto a partir dos dados(Email & Senha) que vieram da Requisição.
			//(UsernamePasswordAuthenticationToken) - da framework Spring Security.
			//Para instanciar é necessário passar (Email, Senha & Lista(que está vazia provisóriamente)).
			UsernamePasswordAuthenticationToken autenticacaoToken = new UsernamePasswordAuthenticationToken(
					credenciaisDTO.getEmail(), credenciaisDTO.getSenha(), new ArrayList<>());

			//Método authenticationManager.authenticate(Token) = Para verificar se o (Email & Senha) são válidos.
			Authentication autenticacao = authenticationManager.authenticate(autenticacaoToken);
			return autenticacao;
		} 
		catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

	// AUTENTICAÇÃO BEM SUCEDIDA.
	//FilterChain = Cadeias de Filtros.
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication autenticacao) throws IOException, ServletException {

		String username = ((UsuarioSpringSecurity) autenticacao.getPrincipal()).getUsername();//Retornar o usuário do Spring Security.
		String token = jwtUtil.gerarToken(username);//Passado o usuário para o Token.
		// response.addHeader("Authorization", "Bearer " + token);
		response.addHeader("Autorizacao", "Usuario-Token " + token);//Adicionando o Token ao cabeçalho da resposta.
	}
	// ------------------------------------------------------------------------------------------------------------

	//Declarando uma outra Classe privada(JWTAuthenticationFailureHandler) que implementa (AuthenticationFailureHandler).
	//Para personalizar o que vai acontecer caso autenticação falhe.
	private class JWTAuthenticationFailureHandler implements AuthenticationFailureHandler {

		@Override
		public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
				AuthenticationException exception) throws IOException, ServletException {

			response.setStatus(401);
			response.setContentType("application/json");
			response.getWriter().append(json());
		}

		private String json() {
			long date = new Date().getTime();

			return "{\"timestamp\": "
			+ date + ", " 
			+ "\"status\": 401, "
			+ "\"error\": \"Não autorizado\", "
			+ "\"message\": \"Email ou senha inválidos\", "
			+ "\"path\": \"/login\"}";
		}
	}

}
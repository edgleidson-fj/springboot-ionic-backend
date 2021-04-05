package com.edgleidson.cursomc.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

//@Component = Para ficar disponivel para outras Classes como componente.

@Component
public class JWTutil {

	//@Value = Chave que está no arquivo (application.properties).
	@Value("${jwt.secret}")
	private String segredo;
	
	@Value("${jwt.expiration}")
	private Long tempoDeExpiracao;
	
	public String gerarToken(String username) {
		return Jwts.builder()
				.setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + tempoDeExpiracao))
				.signWith(SignatureAlgorithm.HS512, segredo.getBytes()) //Algoritmo para assinatura do Token com segredo.
				.compact();
	}
	
	
	public boolean tokenValido(String token) {
		Claims reivindicacoes = getReivindicacoes(token);
		
		if(reivindicacoes != null) {
			String usuario = reivindicacoes.getSubject(); //Pegar o usuário das Reividicações(Claims).
			Date dataDeExpiracao = reivindicacoes.getExpiration(); //Pegar período de expiração das Reividicações(Claims). 
			Date dataAtual = new Date(System.currentTimeMillis()); //Pegar data atual.
			
			if(usuario != null && dataDeExpiracao != null && dataAtual.before(dataDeExpiracao)) { //before() - Antes de.
				return true;
			}
		}
		return false;
	}


	private Claims getReivindicacoes(String token) {
		try {
		return Jwts.parser().setSigningKey(segredo.getBytes()).parseClaimsJws(token).getBody();
		// Para recuperar as Requisições(Claims) a partir de um Token.
		}
		catch(Exception ex) {
			return null;
		}
	}
	
	
	// Método para pegar o usuário a partir do Token.	
	public String getUsername(String token) {
		Claims reivindicacoes = getReivindicacoes(token);
		
		if(reivindicacoes != null) {
			return reivindicacoes.getSubject();
		}
		return null;
	}
		
}
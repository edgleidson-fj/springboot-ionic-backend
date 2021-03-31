package com.edgleidson.cursomc.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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
	
	public  String gerarToken(String username) {
		return Jwts.builder()
				.setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + tempoDeExpiracao))
				.signWith(SignatureAlgorithm.HS512, segredo.getBytes()) //Algoritmo para assinatura do Token com segredo.
				.compact();
	}
}
package com.edgleidson.cursomc.resources;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.edgleidson.cursomc.security.JWTutil;
import com.edgleidson.cursomc.security.UsuarioSpringSecurity;
import com.edgleidson.cursomc.service.UsuarioService;

//Classe para realizar Refresh Token.

@RestController
@RequestMapping(value = "/autenticacao")
public class AutenticacaoResource {

	@Autowired
	private JWTutil jwtUtil;
	
	@RequestMapping(value = "/refresh_token", method = RequestMethod.POST)
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
		UsuarioSpringSecurity usuarioLogado = UsuarioService.usuarioAutenticado();
		String token = jwtUtil.gerarToken(usuarioLogado.getUsername());
		response.addHeader("Autorizacao", "Usuario-Token " + token);
		return ResponseEntity.noContent().build();
	}

}
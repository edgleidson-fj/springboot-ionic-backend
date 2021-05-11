package com.edgleidson.cursomc.resources;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.edgleidson.cursomc.dto.EmailDTO;
import com.edgleidson.cursomc.security.JWTutil;
import com.edgleidson.cursomc.security.UsuarioSpringSecurity;
import com.edgleidson.cursomc.service.AutenticacaoService;
import com.edgleidson.cursomc.service.UsuarioService;

//Classe para realizar REFRESH TOKEN e ESQUECI A SENHA.

@RestController
@RequestMapping(value = "/autenticacao")
public class AutenticacaoResource {

	@Autowired
	private JWTutil jwtUtil;
	@Autowired
	private AutenticacaoService autenticacaoService;
	
	@RequestMapping(value = "/refresh_token", method = RequestMethod.POST)
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
		UsuarioSpringSecurity usuarioLogado = UsuarioService.usuarioAutenticado();
		String token = jwtUtil.gerarToken(usuarioLogado.getUsername());
		response.addHeader("Autorizacao", "Usuario-Token " + token);
		response.addHeader("access-control-expose-headers", "Autorizacao"); //Expondo cabecalho (Autorizacao). 
		return ResponseEntity.noContent().build();
	}

	
	@RequestMapping(value = "/esqueci", method = RequestMethod.POST)
	public ResponseEntity<Void> esqueciSenha(@Valid @RequestBody EmailDTO objDTO) {		
		autenticacaoService.enviarNovaSenha(objDTO.getEmail());		
		return ResponseEntity.noContent().build();
	}
}
package com.edgleidson.cursomc.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.edgleidson.cursomc.security.UsuarioSpringSecurity;

@Service
public class UsuarioService {

	// Método para retornar o usuário que estiver logado no sistema.
	public static UsuarioSpringSecurity usuarioAutenticado() {
		try {
		return (UsuarioSpringSecurity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		catch(Exception ex) {
			return null;
		}
	}
	
}
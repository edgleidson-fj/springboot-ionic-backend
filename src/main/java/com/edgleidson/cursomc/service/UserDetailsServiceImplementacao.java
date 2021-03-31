package com.edgleidson.cursomc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.edgleidson.cursomc.domain.Cliente;
import com.edgleidson.cursomc.repository.ClienteRepository;
import com.edgleidson.cursomc.security.UsuarioSpringSecurity;

//Implementa o método do (UserDetailsService) da framework Spring Security. 

@Service
public class UserDetailsServiceImplementacao implements UserDetailsService{
	
	@Autowired
	private ClienteRepository clienteRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		//CARREGAR O USUÁRIO POR (EMAIL).
		Cliente cli = clienteRepository.findByEmail(email);  
		if(cli == null) {
			throw new UsernameNotFoundException(email);
		}
		
		return new UsuarioSpringSecurity(cli.getId(), cli.getEmail(), cli.getSenha(), cli.getPerfis());
	}
}
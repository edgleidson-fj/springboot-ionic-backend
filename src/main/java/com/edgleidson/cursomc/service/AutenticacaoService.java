package com.edgleidson.cursomc.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.edgleidson.cursomc.domain.Cliente;
import com.edgleidson.cursomc.repository.ClienteRepository;
import com.edgleidson.cursomc.service.exceptions.ObjetoNaoEncontradoException;

// Tabela Unicode =  https://unicode-table.com/pt/

@Service
public class AutenticacaoService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private BCryptPasswordEncoder criptografia;
	@Autowired
	private EmailService emailService;
	
	private Random aleatório = new Random();

	
	public void enviarNovaSenha(String email) {
		Cliente cliente = clienteRepository.findByEmail(email);
		if(cliente == null) {
			throw new ObjetoNaoEncontradoException("Email não encontrado!");
		}
		
		String novaSenha = gerarNovaSenha();
		cliente.setSenha(criptografia.encode(novaSenha));
		clienteRepository.save(cliente);
		
		emailService.envioDeNovaSenhaEmail(cliente, novaSenha);
	}

	
	//Método para gerar nova senha de 10 caracteres.
	private String gerarNovaSenha() {
		char[] vetor = new char[10];
		
		for(int i=0; i<10; i++) {
		vetor[i] = caracteresAleatório();	
		}
		
		return new String(vetor);
	}


	//Método para gerar valores aleatóriamente.
	private char caracteresAleatório() {
		int opcao = aleatório.nextInt(3);
		
		if(opcao == 0) { // Gera um dígito.
			return (char) (aleatório.nextInt(10) + 48); //Caractere de 0-9.
		}else
			if(opcao == 1) { // Gera letra maiúscula.
				return (char) (aleatório.nextInt(26) + 65); //Caractere de A-Z.
			}else { // Gera letra minúscula.
				return (char) (aleatório.nextInt(26) + 97); //Caractere de a-z.
			}		
	}
	
}
package com.edgleidson.cursomc;

import org.springframework.boot.CommandLineRunner;

// CursoMC - Curso de Modelagem Conceitual.

// Aula: 99 Acrescentando mais Produtos para testar INFINITY SCROLL.

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//CommandLineRunner = Executar alguma funcao automaticamente atraves do metodo Run(). 
@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{
		
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	
	@Override
	public void run(String... args) throws Exception {
	}
}
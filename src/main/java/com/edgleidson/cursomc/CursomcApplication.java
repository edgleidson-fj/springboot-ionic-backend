package com.edgleidson.cursomc;

import org.springframework.boot.CommandLineRunner;

// CursoMC - Curso de Modelagem Conceitual.

// Aula: 69 Adicionando senha a Cliente.

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//CommandLineRunner = Executar alguma função automaticamente através do método Run(). 
@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		}
}
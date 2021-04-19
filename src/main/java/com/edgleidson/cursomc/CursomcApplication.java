package com.edgleidson.cursomc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

// CursoMC - Curso de Modelagem Conceitual.

// Aula: 84 Salvando primeiro arquivo no S3.

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.edgleidson.cursomc.service.S3Service;

//CommandLineRunner = Executar alguma funcao automaticamente atraves do metodo Run(). 
@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{
	
	@Autowired
	private S3Service s3service;
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	
	@Override
	public void run(String... args) throws Exception {
		
	s3service.uploadFile("C:\\Users\\Edgleidson\\Pictures\\Carros\\Ftype.jpg");	
		}
}
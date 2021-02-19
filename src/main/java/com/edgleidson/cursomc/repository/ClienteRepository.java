package com.edgleidson.cursomc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.edgleidson.cursomc.domain.Cliente;

@Repository                                          
public interface ClienteRepository  extends JpaRepository<Cliente, Integer>{
	
	//Método que buscar um Cliente passando o email como argumento.
	//(readOly) = Apenas para leitura/consulta, para ficar mais rápido.
	@Transactional(readOnly = true)
	Cliente findByEmail(String email);	
}
package com.edgleidson.cursomc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edgleidson.cursomc.domain.Endereco;

@Repository                                          
public interface EnderecoRepository  extends JpaRepository<Endereco, Integer>{
}
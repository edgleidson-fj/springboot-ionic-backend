package com.edgleidson.cursomc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edgleidson.cursomc.domain.Produto;

// Interface extend JpaRepository
// JpaRepository<Classe, Tipo do ID>

@Repository                                          
public interface ProdutoRepository  extends JpaRepository<Produto, Integer>{

}

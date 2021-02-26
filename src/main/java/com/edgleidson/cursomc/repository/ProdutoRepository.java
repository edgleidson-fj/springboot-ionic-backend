package com.edgleidson.cursomc.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.edgleidson.cursomc.domain.Categoria;
import com.edgleidson.cursomc.domain.Produto;

@Repository                                          
public interface ProdutoRepository  extends JpaRepository<Produto, Integer>{
	
	// Consulta com JPQL.
	//@Param = Parametros que estão na Query JPQL.
	/*@Transactional
	@Query("SELECT DISTINCT obj FROM Produto obj INNER JOIN obj.categorias cat WHERE obj.nome LIKE %:nome% AND cat IN :categorias")
	Page<Produto>pesquisar(@Param("nome") String nome, @Param("categorias")  List<Categoria>categorias, Pageable pageRequest);
	*/
	
	// Consulta com Padrão de nomes da Framework(Spring Data JPA).
	@Transactional(readOnly=true)
	Page<Produto> findDistinctByNomeContainingAllIgnoreCaseAndCategoriasIn(@Param("nome") String nome, @Param("categorias") List<Categoria> categorias, Pageable pageRequest);
}
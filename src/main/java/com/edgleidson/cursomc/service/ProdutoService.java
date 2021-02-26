package com.edgleidson.cursomc.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.edgleidson.cursomc.domain.Categoria;
import com.edgleidson.cursomc.domain.Produto;
import com.edgleidson.cursomc.repository.CategoriaRepository;
import com.edgleidson.cursomc.repository.ProdutoRepository;
import com.edgleidson.cursomc.service.exceptions.ObjetoNaoEncontradoException;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private CategoriaRepository categoriaRepository;

	public Produto buscarPorId(Integer id) {
		Optional<Produto> obj = produtoRepository.findById(id);
		return obj.orElseThrow(() -> new ObjetoNaoEncontradoException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Produto.class.getName()));
	}
	
	//Método de busca paginada.
	public Page<Produto>pesquisar(String nome, List<Integer> ids, Integer pagina, Integer linhasPorPagina, String ordenarPor, String direcao){
		PageRequest pageRequest = PageRequest.of(pagina, linhasPorPagina, Direction.valueOf(direcao), ordenarPor);
		List<Categoria> categorias = categoriaRepository.findAllById(ids);
		
		//return produtoRepository.pesquisar(nome, categorias, pageRequest);
		return produtoRepository.findDistinctByNomeContainingAllIgnoreCaseAndCategoriasIn(nome, categorias, pageRequest);
	}	
}
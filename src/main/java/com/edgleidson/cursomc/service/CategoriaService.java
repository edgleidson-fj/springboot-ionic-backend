package com.edgleidson.cursomc.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import com.edgleidson.cursomc.domain.Categoria;
import com.edgleidson.cursomc.repository.CategoriaRepository;
import com.edgleidson.cursomc.service.exceptions.IntegridadeException;
import com.edgleidson.cursomc.service.exceptions.ObjetoNaoEncontradoException;

@Service
public class CategoriaService {

	// Injeção de dependência.
	@Autowired
	private CategoriaRepository categoriaRepository;

	public Categoria buscarPorId(Integer id) {
		Optional<Categoria> obj = categoriaRepository.findById(id);
		return obj.orElseThrow(() -> new ObjetoNaoEncontradoException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}

	public Categoria inserir(Categoria obj) {
		obj.setId(null);
		return categoriaRepository.save(obj);
	}

	public Categoria atualizar(Categoria obj) {
		buscarPorId(obj.getId());
		return categoriaRepository.save(obj);
	}

	public void excluir(Integer id) {
		buscarPorId(id);
		try {
			categoriaRepository.deleteById(id);
		} catch (DataIntegrityViolationException ex) {
			throw new IntegridadeException("Não é possível excluir uma Categoria que possui Produtos!");
		}
	}
}
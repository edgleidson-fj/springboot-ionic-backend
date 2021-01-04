package com.edgleidson.cursomc.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.edgleidson.cursomc.domain.Categoria;
import com.edgleidson.cursomc.repository.CategoriaRepository;

@Service
public class CategoriaService {

	// Injeção de dependência.
	@Autowired
	private CategoriaRepository categoriaRepository;

	public Categoria buscarPorId(Integer id) {
		Optional<Categoria> obj = categoriaRepository.findById(id);
		return obj.orElse(null);
	}
}

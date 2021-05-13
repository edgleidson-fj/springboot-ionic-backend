package com.edgleidson.cursomc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edgleidson.cursomc.domain.Estado;
import com.edgleidson.cursomc.repository.EstadoRepository;

@Service
public class EstadoService {

	@Autowired
	private EstadoRepository estadoRepository;
	
	public List<Estado> buscarTudo(){
		return estadoRepository.findAllByOrderByNome();
	}
}
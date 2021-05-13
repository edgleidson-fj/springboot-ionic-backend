package com.edgleidson.cursomc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edgleidson.cursomc.domain.Cidade;
import com.edgleidson.cursomc.repository.CidadeRepository;

@Service
public class CidadeService {

	@Autowired
	private CidadeRepository cidadeRepository;
	
	public List<Cidade> buscarPorEstado(Integer estadoId){
		return cidadeRepository.findCidades(estadoId);
	}
}
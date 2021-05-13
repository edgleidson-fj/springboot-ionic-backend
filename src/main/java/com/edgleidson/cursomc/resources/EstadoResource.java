package com.edgleidson.cursomc.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.edgleidson.cursomc.domain.Cidade;
import com.edgleidson.cursomc.domain.Estado;
import com.edgleidson.cursomc.dto.CidadeDTO;
import com.edgleidson.cursomc.dto.EstadoDTO;
import com.edgleidson.cursomc.service.CidadeService;
import com.edgleidson.cursomc.service.EstadoService;

@RestController
@RequestMapping(value = "/estados")
public class EstadoResource {

	@Autowired
	private EstadoService estadoService;
	@Autowired
	private CidadeService cidadeService;
	
	//Buscar todos os Estados.
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<EstadoDTO>> buscarTudo(){
		
		List<Estado> lista = estadoService.buscarTudo();			
		List<EstadoDTO> listaDTO = lista.stream().map(obj -> new EstadoDTO(obj)).collect(Collectors.toList());
		//Convertendo a lista(Estado) em listaDTO(EstadoDTO).
		return ResponseEntity.ok().body(listaDTO);
	}
	
	
	//Buscar todas as Cidades do Estado(ID).
	@RequestMapping(value = "/{estadoId}/cidades",method = RequestMethod.GET)
	public ResponseEntity<List<CidadeDTO>> buscarCidades(@PathVariable Integer estadoId){
	
		List<Cidade> lista = cidadeService.buscarPorEstado(estadoId);
		List<CidadeDTO> listaDTO = lista.stream().map(obj -> new CidadeDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listaDTO);
	}
}
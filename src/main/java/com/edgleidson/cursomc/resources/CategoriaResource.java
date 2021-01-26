package com.edgleidson.cursomc.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.edgleidson.cursomc.domain.Categoria;
import com.edgleidson.cursomc.service.CategoriaService;

//Anotação REST.
//Anotação RequestMapping com nome do EndPoint REST.
@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaService categoriaService;
	
	@RequestMapping(value ="/{id}",method = RequestMethod.GET)
	public ResponseEntity<Categoria> buscarPorId(@PathVariable Integer id) {		
		Categoria obj = categoriaService.buscarPorId(id);		
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> inserir(@RequestBody Categoria obj){
		obj = categoriaService.inserir(obj);
		// Pegando URI junto ID do objeto inserido.
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value = "/{id}",method = RequestMethod.PUT)
	public ResponseEntity<Void> atualizar(@RequestBody Categoria obj, @PathVariable Integer id){
		obj.setId(id);
		obj = categoriaService.atualizar(obj);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value ="/{id}",method = RequestMethod.DELETE)
	public ResponseEntity<Void> excluir(@PathVariable Integer id) {			
			categoriaService.excluir(id);			
		return ResponseEntity.noContent().build();
	}
}
package com.edgleidson.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.edgleidson.cursomc.domain.Categoria;
import com.edgleidson.cursomc.dto.CategoriaDTO;
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
	
	//@Valid = Validação. 
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> inserir(@Valid @RequestBody CategoriaDTO objDTO){
		Categoria obj = categoriaService.ApartirDeUmDTO(objDTO);
		// Pegando URI junto ID do objeto inserido.
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value = "/{id}",method = RequestMethod.PUT)
	public ResponseEntity<Void> atualizar(@Valid @RequestBody CategoriaDTO objDTO, @PathVariable Integer id){
		Categoria obj = categoriaService.ApartirDeUmDTO(objDTO);
		obj.setId(id);
		obj = categoriaService.atualizar(obj);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value ="/{id}",method = RequestMethod.DELETE)
	public ResponseEntity<Void> excluir(@PathVariable Integer id) {			
			categoriaService.excluir(id);			
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<CategoriaDTO>> buscarTudo() {		
		List<Categoria> lista = categoriaService.buscarTudo();
		
		//Convertendo uma Lista(lista) para outra Lista(listaDTO). 
		//Convertendo List<> em Stream, depois reconvertendo em List<>.
		List<CategoriaDTO> listaDTO = lista.stream().map(obj -> new CategoriaDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listaDTO);
	}
	
	//Paginação.
	@RequestMapping(value = "/pagina"  ,method = RequestMethod.GET)
	public ResponseEntity<Page<CategoriaDTO>> buscarPorPagina(
		@RequestParam(value = "pagina", defaultValue = "0")	Integer pagina, 
		@RequestParam(value = "linhasPorPagina", defaultValue = "24")	Integer linhasPorPagina, 
		@RequestParam(value = "ordenarPor", defaultValue = "nome")	String ordenarPor, 
		@RequestParam(value = "direcao", defaultValue = "ASC")	String direcao) {		//Ascendente ou Descendente.
		
		Page<Categoria> lista = categoriaService.paginacao(pagina, linhasPorPagina, ordenarPor, direcao);
		
		//Convertendo Page(lista) para outra Page(listaDTO).
		Page<CategoriaDTO> listaDTO = lista.map(obj -> new CategoriaDTO(obj));
		return ResponseEntity.ok().body(listaDTO);
	}
}
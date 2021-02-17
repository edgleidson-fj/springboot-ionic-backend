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

import com.edgleidson.cursomc.domain.Cliente;
import com.edgleidson.cursomc.dto.ClienteDTO;
import com.edgleidson.cursomc.dto.ClienteNovoDTO;
import com.edgleidson.cursomc.service.ClienteService;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {

	@Autowired
	private ClienteService clienteService;
	
	@RequestMapping(value ="/{id}",method = RequestMethod.GET)
	public ResponseEntity<Cliente> buscarPorId(@PathVariable Integer id) {		
		Cliente obj = clienteService.buscarPorId(id);		
		return ResponseEntity.ok().body(obj);
	}
	
		//@Valid = Validação. 
		@RequestMapping(method = RequestMethod.POST)
		public ResponseEntity<Void> inserir(@Valid @RequestBody ClienteNovoDTO objDTO){
			Cliente obj = clienteService.ApartirDeUmDTO(objDTO);
			obj = clienteService.inserir(obj);
			// Pegando URI junto ID do objeto inserido.
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
			return ResponseEntity.created(uri).build();			
		}
		
		@RequestMapping(value = "/{id}",method = RequestMethod.PUT)
		public ResponseEntity<Void> atualizar(@Valid @RequestBody ClienteDTO objDTO, @PathVariable Integer id){
			Cliente obj = clienteService.ApartirDeUmDTO(objDTO);
			obj.setId(id);
			obj = clienteService.atualizar(obj);
			return ResponseEntity.noContent().build();
		}
		
		@RequestMapping(value ="/{id}",method = RequestMethod.DELETE)
		public ResponseEntity<Void> excluir(@PathVariable Integer id) {			
				clienteService.excluir(id);			
			return ResponseEntity.noContent().build();
		}
		
		@RequestMapping(method = RequestMethod.GET)
		public ResponseEntity<List<ClienteDTO>> buscarTudo() {		
			List<Cliente> lista = clienteService.buscarTudo();
			
			//Convertendo uma Lista(lista) para outra Lista(listaDTO). 
			//Convertendo List<> em Stream, depois reconvertendo em List<>.
			List<ClienteDTO> listaDTO = lista.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
			return ResponseEntity.ok().body(listaDTO);
		}
		
		//Paginação.
		@RequestMapping(value = "/pagina"  ,method = RequestMethod.GET)
		public ResponseEntity<Page<ClienteDTO>> buscarPorPagina(
			@RequestParam(value = "pagina", defaultValue = "0")	Integer pagina, 
			@RequestParam(value = "linhasPorPagina", defaultValue = "24")	Integer linhasPorPagina, 
			@RequestParam(value = "ordenarPor", defaultValue = "nome")	String ordenarPor, 
			@RequestParam(value = "direcao", defaultValue = "ASC")	String direcao) {		//Ascendente ou Descendente.
			
			Page<Cliente> lista = clienteService.paginacao(pagina, linhasPorPagina, ordenarPor, direcao);		
			//Convertendo Page(lista) para outra Page(listaDTO).
			Page<ClienteDTO> listaDTO = lista.map(obj -> new ClienteDTO(obj));
			return ResponseEntity.ok().body(listaDTO);
		}
}
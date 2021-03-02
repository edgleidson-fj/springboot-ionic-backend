package com.edgleidson.cursomc.resources;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.edgleidson.cursomc.domain.Pedido;
import com.edgleidson.cursomc.service.PedidoService;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoResource {

	@Autowired
	private PedidoService pedidoService;
	
	@RequestMapping(value ="/{id}",method = RequestMethod.GET)
	public ResponseEntity<Pedido> buscarPorId(@PathVariable Integer id) {		
		Pedido obj = pedidoService.buscarPorId(id);		
		return ResponseEntity.ok().body(obj);
	}
	
		@RequestMapping(method = RequestMethod.POST)
		public ResponseEntity<Void> inserir(@Valid @RequestBody Pedido obj){
			obj = pedidoService.inserir(obj);
			// Pegando URI junto ID do objeto inserido.
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
			return ResponseEntity.created(uri).build();
		}
}
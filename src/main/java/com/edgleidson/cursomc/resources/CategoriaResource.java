package com.edgleidson.cursomc.resources;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

//Anotação REST.
//Anotação RequestMapping com nome do END Point REST.
@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {

	//Verbo HTTP (GET) - Para pegar dados.
	@RequestMapping(method = RequestMethod.GET)
	public String listar() {
		return "REST está funcionando.";
	}
}

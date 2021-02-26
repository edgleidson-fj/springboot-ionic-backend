package com.edgleidson.cursomc.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edgleidson.cursomc.domain.Produto;
import com.edgleidson.cursomc.dto.ProdutoDTO;
import com.edgleidson.cursomc.resources.utils.URL;
import com.edgleidson.cursomc.service.ProdutoService;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoResource {

	@Autowired
	private ProdutoService produtoService;
	
	@RequestMapping(value ="/{id}",method = RequestMethod.GET)
	public ResponseEntity<Produto> buscarPorId(@PathVariable Integer id) {		
		Produto obj = produtoService.buscarPorId(id);		
		return ResponseEntity.ok().body(obj);
	}
	
	//Paginação.
		@RequestMapping(method = RequestMethod.GET)
		public ResponseEntity<Page<ProdutoDTO>> buscarPorPagina(
			@RequestParam(value = "nome", defaultValue = " ")	String nome,
			@RequestParam(value = "categorias", defaultValue = " ")	String categorias, 
			@RequestParam(value = "pagina", defaultValue = "0")	Integer pagina, 
			@RequestParam(value = "linhasPorPagina", defaultValue = "24")	Integer linhasPorPagina, 
			@RequestParam(value = "ordenarPor", defaultValue = "nome")	String ordenarPor, 
			@RequestParam(value = "direcao", defaultValue = "ASC")	String direcao) {		//Ascendente ou Descendente.
			
			String nomeDecodificado = URL.decodificarParametro(nome);
			List<Integer> ids = URL.converterStringNaListaDeInteiro(categorias);
			
			Page<Produto> lista = produtoService.pesquisar(nomeDecodificado,ids, pagina, linhasPorPagina, ordenarPor, direcao);		
			//Convertendo Page(lista) para outra Page(listaDTO).
			Page<ProdutoDTO> listaDTO = lista.map(obj -> new ProdutoDTO(obj));
			return ResponseEntity.ok().body(listaDTO);
		}		
}
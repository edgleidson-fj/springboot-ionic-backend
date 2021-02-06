package com.edgleidson.cursomc.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.edgleidson.cursomc.domain.Cliente;
import com.edgleidson.cursomc.dto.ClienteDTO;
import com.edgleidson.cursomc.repository.ClienteRepository;
import com.edgleidson.cursomc.service.exceptions.IntegridadeException;
import com.edgleidson.cursomc.service.exceptions.ObjetoNaoEncontradoException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	public Cliente buscarPorId(Integer id) {
		Optional<Cliente> obj = clienteRepository.findById(id);
		return obj.orElseThrow(() -> new ObjetoNaoEncontradoException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
	
	public Cliente atualizar(Cliente obj) {
		Cliente novoObj = buscarPorId(obj.getId());
		atualizarDados(novoObj,obj);
		return clienteRepository.save(novoObj);
	}

	public void excluir(Integer id) {
		buscarPorId(id);
		try {
			clienteRepository.deleteById(id);
		} catch (DataIntegrityViolationException ex) {
			throw new IntegridadeException("Não é possível excluir o Cliente porque tem outras entidades relacionadas!");
		}
	}
	
	public List<Cliente> buscarTudo(){
		return clienteRepository.findAll();
	}
	
	//Paginação.
	public Page<Cliente> paginacao(Integer pagina, Integer linhasPorPagina, String ordenarPor, String direcao){
		PageRequest pageRequest = PageRequest.of(pagina, linhasPorPagina, Direction.valueOf(direcao), ordenarPor);
		return clienteRepository.findAll(pageRequest);
	}
	
	//Método auxiliar para instanciar uma Cliente a partir de um DTO.
	public Cliente ApartirDeUmDTO(ClienteDTO objDTO) {
		return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), null, null);
		//Obs: CPF e Tipo nulo, porque o DTO não tem esses dados.
	}
	
	//Método auxiliar para atualização.
	private void atualizarDados(Cliente novoObj, Cliente obj) {
		novoObj.setNome(obj.getNome());
		novoObj.setEmail(obj.getEmail());
	}
}
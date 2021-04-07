package com.edgleidson.cursomc.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edgleidson.cursomc.domain.Cidade;
import com.edgleidson.cursomc.domain.Cliente;
import com.edgleidson.cursomc.domain.Endereco;
import com.edgleidson.cursomc.domain.enums.Perfil;
import com.edgleidson.cursomc.domain.enums.TipoCliente;
import com.edgleidson.cursomc.dto.ClienteDTO;
import com.edgleidson.cursomc.dto.ClienteNovoDTO;
import com.edgleidson.cursomc.repository.ClienteRepository;
import com.edgleidson.cursomc.repository.EnderecoRepository;
import com.edgleidson.cursomc.security.UsuarioSpringSecurity;
import com.edgleidson.cursomc.service.exceptions.AutorizacaoException;
import com.edgleidson.cursomc.service.exceptions.IntegridadeException;
import com.edgleidson.cursomc.service.exceptions.ObjetoNaoEncontradoException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private BCryptPasswordEncoder criptografia;
	

	public Cliente buscarPorId(Integer id) {		
		UsuarioSpringSecurity usuarioLogado = UsuarioService.usuarioAutenticado();
		
		//usuarioLogado igual nulo OU usuarioLogado diferente de (ADMIN) & usuarioLogado diferente do ID do Usuario.
		if(usuarioLogado == null || !usuarioLogado.temFuncao(Perfil.ADMIN) && !id.equals(usuarioLogado.getId())) {
			throw new AutorizacaoException("Acesso negado!");
		}
		
		Optional<Cliente> obj = clienteRepository.findById(id);
		return obj.orElseThrow(() -> new ObjetoNaoEncontradoException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
	
	
	@Transactional
	public Cliente inserir(Cliente obj) {
		obj.setId(null);
		obj = clienteRepository.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;		
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
	
	
	//Método auxiliar para instanciar um Cliente a partir de um DTO.
	public Cliente ApartirDeUmDTO(ClienteDTO objDTO) {
		return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), null, null, null);
		//Obs: CPF e Tipo nulo, porque o DTO não tem esses dados.
	}
	
	
	//Sobrecarga(ApartirDeUmDTO).
	public Cliente ApartirDeUmDTO(ClienteNovoDTO objDTO) {
		Cliente cli = new Cliente(null, objDTO.getNome(), objDTO.getEmail(), objDTO.getCpfOuCnpj(), TipoCliente.toEnum(objDTO.getTipo()), criptografia.encode(objDTO.getSenha()));
		Cidade cid = new Cidade(objDTO.getCidadeId(), null, null);
		Endereco end = new Endereco(null, objDTO.getLogradouro(), objDTO.getNumero(), objDTO.getComplemento(), objDTO.getBairro(), objDTO.getCep(), cli, cid);
		cli.getEnderecos().add(end);
		cli.getTelefone().add(objDTO.getTelefone1());
		//Telefones opcionais.
		if(objDTO.getTelefone2()!=null) {
			cli.getTelefone().add(objDTO.getTelefone2());
		}
		if(objDTO.getTelefone3()!=null) {
			cli.getTelefone().add(objDTO.getTelefone3());
		}
		return cli;
	}
	
	
	//Método auxiliar para atualização.
	private void atualizarDados(Cliente novoObj, Cliente obj) {
		novoObj.setNome(obj.getNome());
		novoObj.setEmail(obj.getEmail());
	}
	
}
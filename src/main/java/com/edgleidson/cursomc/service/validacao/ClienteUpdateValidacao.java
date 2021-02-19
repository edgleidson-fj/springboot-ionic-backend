package com.edgleidson.cursomc.service.validacao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.edgleidson.cursomc.domain.Cliente;
import com.edgleidson.cursomc.dto.ClienteDTO;
import com.edgleidson.cursomc.repository.ClienteRepository;
import com.edgleidson.cursomc.resources.exceptions.MensagemDoCampo;

//Classe criada para custumizar a validação de Email, na atualização do Cliente.
public class ClienteUpdateValidacao implements ConstraintValidator<ClienteUpdate, ClienteDTO> {

	@Autowired
	private HttpServletRequest request; //Requisição Web.
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Override
	public void initialize(ClienteUpdate ann) {
	}

	@Override
	public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) {
		
		//Método para pegar o ID do Cliente que está sendo atualizado, via URI.
		//Map<Chave, Valor>
		Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Integer uriID = Integer.parseInt(map.get("id"));
		
		List<MensagemDoCampo> lista = new ArrayList<>();
		
		// inclua os testes aqui, inserindo erros na lista.
				
		//Email - Checando o ID do cliente na atualização.
		Cliente aux = clienteRepository.findByEmail(objDto.getEmail());
		if(aux != null && !aux.getId().equals(uriID)) {
			lista.add(new MensagemDoCampo("email", "Email já existente."));
		}
		
		for (MensagemDoCampo x : lista) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(x.getMensagem()).addPropertyNode(x.getNomeDoCampo())
					.addConstraintViolation();
		}
		return lista.isEmpty();
	}
}
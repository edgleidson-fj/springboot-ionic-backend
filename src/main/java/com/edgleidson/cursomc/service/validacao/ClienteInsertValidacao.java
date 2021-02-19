package com.edgleidson.cursomc.service.validacao;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.edgleidson.cursomc.domain.Cliente;
import com.edgleidson.cursomc.domain.enums.TipoCliente;
import com.edgleidson.cursomc.dto.ClienteNovoDTO;
import com.edgleidson.cursomc.repository.ClienteRepository;
import com.edgleidson.cursomc.resources.exceptions.MensagemDoCampo;
import com.edgleidson.cursomc.service.validacao.utils.BR;

//Classe criada para custumizar a validação de CPF, CNPJ e Email.
public class ClienteInsertValidacao implements ConstraintValidator<ClienteInsert, ClienteNovoDTO> {

	@Autowired
	ClienteRepository clienteRepository;
	
	@Override
	public void initialize(ClienteInsert ann) {
	}

	@Override
	public boolean isValid(ClienteNovoDTO objDto, ConstraintValidatorContext context) {
		List<MensagemDoCampo> lista = new ArrayList<>();
		
		// inclua os testes aqui, inserindo erros na lista
		
		//CPF
		if(objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCodigo()) && !BR.isValidCPF(objDto.getCpfOuCnpj())) {
			lista.add(new MensagemDoCampo("cpfOuCnpj", "CPF Inválido."));
		}		
		//CNPJ
		if(objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCodigo()) && !BR.isValidCNPJ(objDto.getCpfOuCnpj())) {
			lista.add(new MensagemDoCampo("cpfOuCnpj", "CNPJ Inválido."));
		}		
		//Email
		Cliente aux = clienteRepository.findByEmail(objDto.getEmail());
		if(aux != null) {
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
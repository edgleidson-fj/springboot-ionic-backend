package com.edgleidson.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.edgleidson.cursomc.service.BDService;
import com.edgleidson.cursomc.service.EmailService;
import com.edgleidson.cursomc.service.MockEmailService;

//Obs: Essa classe sera utilizada especificamente quando o perfil de teste(test) ->
//  -> estiver ativo no "application.properties".

@Configuration
@Profile("test")
public class TesteConfig {
	
	@Autowired
	private BDService bdService;

	//@Bean = Para diponibilizar que o metodo vire um componente dentro do sistema/codigo fonte.
	@Bean
	public boolean instanciarBancoDeDados() throws ParseException {
		bdService.instaciarBancoDeDadosTeste();
		return true;
	}
	
	@Bean
	public EmailService emailService() {
		return new MockEmailService();
	}
}
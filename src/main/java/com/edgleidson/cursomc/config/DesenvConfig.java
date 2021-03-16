package com.edgleidson.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.edgleidson.cursomc.service.BDService;
import com.edgleidson.cursomc.service.EmailService;
import com.edgleidson.cursomc.service.SmtpEmailService;

//Obs: Essa classe será utilizada específicamente quando o perfil de desenvolvimento(dev) ->
//  -> estiver ativo no "application.properties".

@Configuration
@Profile("dev")
public class DesenvConfig {
	
	@Autowired
	private BDService bdService;
	
	// Pegando valor da chave dentro do arquivo(application-dev.properties).
	// Valores: none, create, create-drop, validate, e update.
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String estrategia;
	
	@Bean
	public boolean instanciarBancoDeDados() throws ParseException {
		if(!"create".equals(estrategia)) {//Se a Estratégia não for igual a palavra "create".
			return false;
		}		
		bdService.instaciarBancoDeDadosTeste();
		return true;
	}
	
	@Bean
	public EmailService emailService() {
		return new SmtpEmailService();
	}
}
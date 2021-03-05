package com.edgleidson.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.edgleidson.cursomc.service.BDService;

//Obs: Essa classe será utilizada específicamente quando o perfil de teste(test) ->
//  -> estiver ativo no "application.properties".

@Configuration
@Profile("test")
public class TesteConfig {
	
	@Autowired
	private BDService bdService;

	@Bean
	public boolean instanciarBancoDeDados() throws ParseException {
		bdService.instaciarBancoDeDadosTeste();
		return true;
	}
}
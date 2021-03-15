package com.edgleidson.cursomc.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage; 

//Extends da Classe Abstrata (AbstratoEmailService).

public class MockEmailService extends AbstratoEmailService{
	
	private static final Logger LOG = LoggerFactory.getLogger(MockEmailService.class);

	@Override
	public void envioDeEmail(SimpleMailMessage msg) {
		LOG.info("Simulando envio de email ...");
		LOG.info(msg.toString());
		LOG.info("Email enviado.");
	}
}
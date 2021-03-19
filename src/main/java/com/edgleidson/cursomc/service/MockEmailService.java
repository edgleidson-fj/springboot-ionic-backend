package com.edgleidson.cursomc.service;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage; 

// SubClasse.
//Extends da Classe Abstrata (AbstratoEmailService).

public class MockEmailService extends AbstratoEmailService{
	
	private static final Logger LOG = LoggerFactory.getLogger(MockEmailService.class);

	@Override
	public void envioDeEmail(SimpleMailMessage msg) {
		LOG.info("Simulando envio de email ...");
		LOG.info(msg.toString());
		LOG.info("Email enviado.");
	}

	@Override
	public void envioHtmlEmail(MimeMessage msg) {
		LOG.info("Simulando envio de email Html...");
		LOG.info(msg.toString());
		LOG.info("Email Html enviado.");
	}
}
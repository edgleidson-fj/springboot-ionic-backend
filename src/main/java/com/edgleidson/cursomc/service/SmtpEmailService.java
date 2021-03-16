package com.edgleidson.cursomc.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

// Extends da classe abstrata(AbstratoEmailService).

@Service
public class SmtpEmailService extends AbstratoEmailService{

	@Autowired
	private MailSender remetenteDeEmail;
	
	private static final Logger LOG = LoggerFactory.getLogger(SmtpEmailService.class);
	
	@Override
	public void envioDeEmail(SimpleMailMessage msg) {
		remetenteDeEmail.send(msg); //Enviando email.
		LOG.info("Email enviado.");
	}
}
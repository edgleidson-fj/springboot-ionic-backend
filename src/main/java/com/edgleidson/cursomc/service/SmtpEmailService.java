package com.edgleidson.cursomc.service;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

// SubClasse.
// Extends da classe abstrata(AbstratoEmailService).

@Service
public class SmtpEmailService extends AbstratoEmailService{

	@Autowired
	private MailSender remetenteDeEmail;
	@Autowired
	private JavaMailSender javaMailSender;
	
	private static final Logger LOG = LoggerFactory.getLogger(SmtpEmailService.class);
	
	@Override
	public void envioDeEmail(SimpleMailMessage msg) {
		remetenteDeEmail.send(msg); //Enviando email.
		LOG.info("Email enviado.");
	}

	@Override
	public void envioHtmlEmail(MimeMessage msg) {
		javaMailSender.send(msg); //Enviando email Html.
		LOG.info("Email enviado.");
	}
}
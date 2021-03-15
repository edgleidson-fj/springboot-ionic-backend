package com.edgleidson.cursomc.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.edgleidson.cursomc.domain.Pedido;

// Classe Abstrata.
// Está implementando a Interface(EmailService)

public abstract class AbstratoEmailService implements EmailService {
	
	// Pegando valor da chave dentro do arquivo(application.properties).
	@Value("${default.sender}")
	private String sender; // Sender = Remetente.

	@Override
	public void envioDeConfirmacaoDePedido(Pedido obj) {
		SimpleMailMessage sm = prepareSimpleMailMessageParaPedido(obj);
		envioDeEmail(sm);
	}

	protected SimpleMailMessage prepareSimpleMailMessageParaPedido(Pedido obj) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(obj.getCliente().getEmail()); //Destinatário do Email.
		sm.setFrom(sender); //Remetente do Email.
		sm.setSubject("Pedido confirmado! Código: "+obj.getId()); //Assunto do Email.		
		sm.setSentDate(new Date(System.currentTimeMillis())); //Data do Email.
		//System.currentTimeMillis() - Para garantir que pegue a data do Servidor.
		sm.setText(obj.toString()); //Corpo do Email.
		return sm;
	}
}
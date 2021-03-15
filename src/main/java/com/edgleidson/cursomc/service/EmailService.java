package com.edgleidson.cursomc.service;

// Interface.

import org.springframework.mail.SimpleMailMessage;

import com.edgleidson.cursomc.domain.Pedido;

public interface EmailService {

	void envioDeConfirmacaoDePedido(Pedido obj);
	
	void envioDeEmail(SimpleMailMessage msg);
}
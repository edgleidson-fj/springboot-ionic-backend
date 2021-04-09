package com.edgleidson.cursomc.service;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import com.edgleidson.cursomc.domain.Cliente;
import com.edgleidson.cursomc.domain.Pedido;

//Interface.

public interface EmailService {

	// Versão em texto.
	void envioDeConfirmacaoDePedido(Pedido obj);
	void envioDeEmail(SimpleMailMessage msg);

	// Versão em HTML.	
	void envioDeConfirmacaoHtmlEmail(Pedido obj);
	void envioHtmlEmail(MimeMessage msg);
	
	// Enviar nova senha.
	void envioDeNovaSenhaEmail(Cliente cliente, String novaSenha);
}
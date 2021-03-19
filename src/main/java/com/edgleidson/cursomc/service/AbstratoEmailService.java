package com.edgleidson.cursomc.service;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.edgleidson.cursomc.domain.Pedido;

// Classe Abstrata.
// Está implementando a Interface(EmailService)

public abstract class AbstratoEmailService implements EmailService {
	
	// Pegando valor da chave dentro do arquivo(application.properties).
	@Value("${default.sender}")
	private String sender; // Sender = Remetente.
	
	@Autowired
	private TemplateEngine templateEngine; //TemplateEngine do Thymeleaf.
	@Autowired
	private JavaMailSender javaMailSender;

	//--- Versão em texto.
	
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
	
	//---- Versão em HTML.-------------- 
	
	//Método para injetar os dados do Pedido no Template HTML (email/confirmacaoPedido.html). 
	protected String htmlDeTemplatePedido(Pedido obj) {
		Context context = new Context(); //Context do Thymeleaf. 
		
		//Enviar o Objeto para o Template.
		context.setVariable("pedido", obj); //(Apelido que está no arquivo, Objeto). 
		
		//Retornando o que foi processado no Template(Diretório, CONTEXT).
		return templateEngine.process("email/confirmacaoPedido", context); 
	}
	
	
	@Override
	public void envioDeConfirmacaoHtmlEmail(Pedido obj) {
		try {
		MimeMessage mm = prepareMimeMessageAPartirDeUmPedido(obj);
		envioHtmlEmail(mm);
		}
		catch(MessagingException ex) {		
			//Se apresentar erro envio o email na versão em texto.
			envioDeConfirmacaoDePedido(obj);			
		}
	}

	// Protected = Para possibilitar a reimplementação nas subClasses.
	protected MimeMessage prepareMimeMessageAPartirDeUmPedido(Pedido obj) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true);
		mmh.setTo(obj.getCliente().getEmail()); //Destinatário.
		mmh.setFrom(sender); //Remetente.
		mmh.setSubject("Teste de Email"); //Assunto do email
		mmh.setSentDate(new Date(System.currentTimeMillis())); //Data do email.
		mmh.setText(htmlDeTemplatePedido(obj),true); //Corpo do email.
		return mimeMessage;
	}
}
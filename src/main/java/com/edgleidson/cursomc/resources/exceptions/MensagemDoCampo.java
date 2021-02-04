package com.edgleidson.cursomc.resources.exceptions;

import java.io.Serializable;

//Classe auxiliar para carregar os dados(Validação).

public class MensagemDoCampo implements Serializable{
	private static final long serialVersionUID = 1L;

	private String nomeDoCampo;
	private String mensagem;
	
	public MensagemDoCampo() {
		}

	public MensagemDoCampo(String nomeDoCampo, String mensagem) {
		super();
		this.nomeDoCampo = nomeDoCampo;
		this.mensagem = mensagem;
	}

	public String getNomeDoCampo() {
		return nomeDoCampo;
	}

	public void setNomeDoCampo(String nomeDoCampo) {
		this.nomeDoCampo = nomeDoCampo;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}	
}
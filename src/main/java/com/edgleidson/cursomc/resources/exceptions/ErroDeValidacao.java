package com.edgleidson.cursomc.resources.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ErroDeValidacao extends ErroPadrao {
	private static final long serialVersionUID = 1L;

	private List<MensagemDoCampo> erros = new ArrayList<>();

	public ErroDeValidacao(Integer status, String msg, Long timestamp) {
		super(status, msg, timestamp);
	}

	public List<MensagemDoCampo> getErros() {
		return erros;
	}

	//Usar esse método ao invés do Set, devido a ser uma lista.
	public void addErro(String nomeDoCampo, String mensagem) {
		erros.add(new MensagemDoCampo(nomeDoCampo, mensagem));
	}
}
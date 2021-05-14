package com.edgleidson.cursomc.resources.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ErroDeValidacao extends ErroPadrao {
	private static final long serialVersionUID = 1L;

	private List<MensagemDoCampo> erros = new ArrayList<>();	

	//Obs: Nao deve ter lista no Construtor.
	public ErroDeValidacao(Long timestamp, Integer status, String erro, String msg, String path) {
		super(timestamp, status, erro, msg, path);
	}

	public List<MensagemDoCampo> getErros() {
		return erros;
	}

	//Usar esse metodo ao inves do Set, devido a ser uma lista.
	public void addErro(String nomeDoCampo, String mensagem) {
		erros.add(new MensagemDoCampo(nomeDoCampo, mensagem));
	}
}
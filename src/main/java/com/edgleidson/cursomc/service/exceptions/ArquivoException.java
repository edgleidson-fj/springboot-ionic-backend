package com.edgleidson.cursomc.service.exceptions;

public class ArquivoException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public ArquivoException(String msg) {
		super(msg);
	}
	
	public ArquivoException(String msg, Throwable causa) {
		super(msg, causa);
	}
}
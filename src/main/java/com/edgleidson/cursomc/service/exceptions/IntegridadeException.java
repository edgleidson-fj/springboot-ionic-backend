package com.edgleidson.cursomc.service.exceptions;

public class IntegridadeException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public IntegridadeException(String msg) {
		super(msg);
	}
	
	public IntegridadeException(String msg, Throwable causa) {
		super(msg, causa);
	}
}
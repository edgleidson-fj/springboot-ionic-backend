package com.edgleidson.cursomc.resources.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.edgleidson.cursomc.service.exceptions.IntegridadeException;
import com.edgleidson.cursomc.service.exceptions.ObjetoNaoEncontradoException;

//Manipulador de Erros.

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ObjetoNaoEncontradoException.class)
	public ResponseEntity<ErroPadrao> ObjetoNaoEncontrado(ObjetoNaoEncontradoException ex, HttpServletRequest request){
		
		ErroPadrao erroPadrao = new ErroPadrao(HttpStatus.NOT_FOUND.value(), ex.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erroPadrao);
	}
	
	@ExceptionHandler(IntegridadeException.class)
	public ResponseEntity<ErroPadrao> Integridade(IntegridadeException ex, HttpServletRequest request){
		
		ErroPadrao erroPadrao = new ErroPadrao(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroPadrao);
	}
}
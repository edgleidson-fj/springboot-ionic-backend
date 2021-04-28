package com.edgleidson.cursomc.resources.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.edgleidson.cursomc.service.exceptions.ArquivoException;
import com.edgleidson.cursomc.service.exceptions.AutorizacaoException;
import com.edgleidson.cursomc.service.exceptions.IntegridadeException;
import com.edgleidson.cursomc.service.exceptions.ObjetoNaoEncontradoException;

//Manipulador de Erros.

@ControllerAdvice
public class ResourceExceptionHandler {

	//OBJETO NAO ENCONTRADO
	@ExceptionHandler(ObjetoNaoEncontradoException.class)
	public ResponseEntity<ErroPadrao> objetoNaoEncontrado(ObjetoNaoEncontradoException ex, HttpServletRequest request){
		
		ErroPadrao erroPadrao = new ErroPadrao(HttpStatus.NOT_FOUND.value(), ex.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erroPadrao);
	}
	
	//INTEGRIDADE
	@ExceptionHandler(IntegridadeException.class)
	public ResponseEntity<ErroPadrao> integridade(IntegridadeException ex, HttpServletRequest request){
		
		ErroPadrao erroPadrao = new ErroPadrao(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroPadrao);
	}	
	
	//VALIDACAO
	//MethodArgumentNotValidException - Excecao para Validacao.
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErroPadrao> validacao(MethodArgumentNotValidException ex, HttpServletRequest request){
		
		ErroDeValidacao erroDeValidacao = new ErroDeValidacao(HttpStatus.BAD_REQUEST.value(),"Erro de validação", System.currentTimeMillis());
		//FOR - Para pecorrer a lista de erro que ja consta na excerção(MethodArgumentNotValidException).
		for(FieldError x : ex.getBindingResult().getFieldErrors()) {
			erroDeValidacao.addErro(x.getField(), x.getDefaultMessage());
		}		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroDeValidacao);
	}
	
	//AUTORIZACAO
	@ExceptionHandler(AutorizacaoException.class)
	public ResponseEntity<ErroPadrao> autorizacao(AutorizacaoException ex, HttpServletRequest request){
		
		ErroPadrao erroPadrao = new ErroPadrao(HttpStatus.FORBIDDEN.value(), ex.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(erroPadrao);
	}
	
	//ARQUIVO
	@ExceptionHandler(ArquivoException.class)
	public ResponseEntity<ErroPadrao> arquivo(ArquivoException ex, HttpServletRequest request){
		
		ErroPadrao erroPadrao = new ErroPadrao(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroPadrao);
	}
	
	//AMAZON SERVICE
	@ExceptionHandler(AmazonServiceException.class)
	public ResponseEntity<ErroPadrao> amazonService(AmazonServiceException ex, HttpServletRequest request){
		
		HttpStatus codigoHTTP = HttpStatus.valueOf(ex.getErrorCode());		
		ErroPadrao erroPadrao = new ErroPadrao(codigoHTTP.value(), ex.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(codigoHTTP).body(erroPadrao);
	}
	
	//AMAZON CLIENT
	@ExceptionHandler(AmazonClientException.class)
	public ResponseEntity<ErroPadrao> amazonClient(AmazonClientException ex, HttpServletRequest request){
		
		ErroPadrao erroPadrao = new ErroPadrao(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroPadrao);
	}
	
	//AMAZON S3
	@ExceptionHandler(AmazonS3Exception.class)
	public ResponseEntity<ErroPadrao> amazonS3(AmazonS3Exception ex, HttpServletRequest request){
		
		ErroPadrao erroPadrao = new ErroPadrao(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroPadrao);
	}
}
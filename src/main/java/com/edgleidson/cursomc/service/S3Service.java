package com.edgleidson.cursomc.service;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;


@Service
public class S3Service {
	
	private Logger LOG = LoggerFactory.getLogger(S3Service.class);

	@Autowired
	private AmazonS3 s3cliente;

	// @Value - Pegando valor da chave dentro do arquivo(application.properties).
	@Value("${s3.bucket}")
	private String bucketNome;


	// Metodo para subir o arquivo para o S3 - Amazon AWS.
	public void uploadFile(String diretorioArquivoLocal) {
		try {
			File arquivo = new File(diretorioArquivoLocal);

			LOG.info("Iniciando upload");
			s3cliente.putObject(new PutObjectRequest(bucketNome, "Imagem.jpg", arquivo));
			LOG.info("Upload finalizado");
		} 
		catch (AmazonServiceException ex) {
			LOG.info("AmazonServiceException: "+ ex.getMessage());
			LOG.info("Código de Status: "+ ex.getErrorCode());
		} 
		catch (AmazonClientException ex) {
			LOG.info("AmazonClientException: "+ ex.getMessage());
		}
	}
}
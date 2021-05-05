package com.edgleidson.cursomc.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.edgleidson.cursomc.service.exceptions.ArquivoException;

@Service
public class S3Service {

	private Logger LOG = LoggerFactory.getLogger(S3Service.class);

	@Autowired
	private AmazonS3 s3cliente;

	// @Value - Pegando valor da chave dentro do arquivo(application.properties).
	@Value("${s3.bucket}")
	private String bucketNome;

	// Metodo para subir o arquivo para o S3 - Amazon AWS.
	// MultipartFile = Eh o tipo que o endpoint vai receber. 
	// ""   ""    "" = Eh o tipo do Spring correspondente ao arquivo que envia na requisição.
	// URI = Para retornar o endereco web do novo recurso que foi gerado.
	public URI uploadArquivo(MultipartFile multipartFile) {
		try {
			String nomeDoArquivo = multipartFile.getOriginalFilename();
			
			//InputStream = Eh um objeto basico de leitura do (Java.IO).
			//Encapsula o processamento de leitura a partir de um arquivo de origem.
			InputStream inputStream = multipartFile.getInputStream();
			String tipoDoArquivo = multipartFile.getContentType();
		
			return uploadArquivo(inputStream, nomeDoArquivo, tipoDoArquivo);
		}
		catch(IOException ex) {
			throw new ArquivoException("Erro de IO: " + ex.getMessage());
		}
	}

	//Sobrecarga do metodo acima.
	public URI uploadArquivo(InputStream inputStream, String nomeDoArquivo, String tipoDoArquivo) {
		try {
			//ObjectMetadata - Da biblioteca da Amazon.
			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentType(tipoDoArquivo);

			LOG.info("Iniciando upload");
			s3cliente.putObject(bucketNome, nomeDoArquivo, inputStream, metadata);
			LOG.info("Upload finalizado");
			
			//Retornando a URL e ja convertendo em URI.
			return s3cliente.getUrl(bucketNome, nomeDoArquivo).toURI();
		} 
		catch (URISyntaxException ex) {
			throw new ArquivoException("Erro ao converter URL para URI");
		}
	}
}
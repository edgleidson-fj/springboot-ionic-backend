package com.edgleidson.cursomc.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

//Classe de configuracao do S3 - Amazon AWS.

@Configuration
public class S3Config {

	// @Value - Pegando valor da chave dentro do arquivo(application.properties).
	@Value("${aws.access_key_id}")
	private String awsId;
	
	@Value("${aws.secret_access_key}")
	private String awsChave;
	
	@Value("${s3.region}")
	private String regiao;
	
	// @Bean - Para disponibiliza o metodo como componente no codigo fonte.
	@Bean
	public AmazonS3 s3cliente() {
		BasicAWSCredentials awsCredenciais = new BasicAWSCredentials(awsId, awsChave);
		
		AmazonS3 s3cliente = AmazonS3ClientBuilder.standard().withRegion(Regions.fromName(regiao))
				.withCredentials(new AWSStaticCredentialsProvider(awsCredenciais)).build();
				
		return s3cliente;
	}
}
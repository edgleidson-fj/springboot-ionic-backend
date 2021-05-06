package com.edgleidson.cursomc.service;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.edgleidson.cursomc.service.exceptions.ArquivoException;

@Service
public class ImagemService {

	public BufferedImage pegarImagemJPGdoArquivo(MultipartFile uploadArquivo) {
		// Pegando extensao do arquivo.
		// FilenameUtils - da biblioteca COMMONS-IO.
		String extensao = FilenameUtils.getExtension(uploadArquivo.getOriginalFilename());

		// Se a extensao nao for (jpg) nem (png).
		if (!"png".equals(extensao) && !"jpg".equals(extensao)) {
			throw new ArquivoException("Somente imagens PNG e JPG são permitidos.");
		}
		
		try {
			// Tentando obter um BufferedImage a partir de um MultipartFile.
			BufferedImage img = ImageIO.read(uploadArquivo.getInputStream());
			
			// Se a extensao do arquivo for (png), sera convertido em (jpg).
			if ("png".equals(extensao)) {
				img = converterPNGparaJPG(img);
			}
			return img;
		} 
		catch (IOException ex) {
			throw new ArquivoException("Erro ao ler o arquivo.");
		}
	}

	
	// Metodo para converter um BufferedImage(PNG) para BufferedImage(JPG).
	public BufferedImage converterPNGparaJPG(BufferedImage img) {
		BufferedImage imagemJPG = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
		imagemJPG.createGraphics().drawImage(img, 0, 0, Color.WHITE, null);
		//Color.WHITE = Devido alguns PNG que tem fundo transparente.
		return imagemJPG;
	}

	
	// Metodo para obter um InputStream a partir de um BufferedImage.
	// Isso eh necessario, porque o metodo que faz o upload para S3-Amazon AWS, recebe um InputStream.
	public InputStream getInputStream(BufferedImage img, String extensao) {
		try {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			ImageIO.write(img, extensao, outputStream);
			return new ByteArrayInputStream(outputStream.toByteArray());
		} 
		catch (IOException ex) {
			throw new ArquivoException("Erro ao ler o arquivo.");
		}
	}
	
	
	// Metodo para recortar imagem.
	public BufferedImage recortarQuadrado(BufferedImage imagemOriginal) {
		//Condicional ternaria (A <= B) ? A : B.
		//Se ALTURA for menor ou igual LARGURA ? entao Minimo=ALTURA : se nao Minimo=LARGURA.
		int minimo = (imagemOriginal.getHeight() <= imagemOriginal.getWidth()) ? imagemOriginal.getHeight() : imagemOriginal.getWidth();
		
		//Recortar imagem, atraves da coodernada.
		return Scalr.crop(
				imagemOriginal,					
				(imagemOriginal.getWidth()/2 - (minimo/2)), //Na metade da LARGURA menos(-) a metade do Minimo.
				(imagemOriginal.getHeight()/2 - (minimo/2)),//Na metade da ALTURA menos(-) a metade do Minimo.
						minimo, //Quanto sera recortado na LARGURA.
						minimo);//Quanto sera recortado na ALTURA.				
	}
	
	
	// Metodo para redimensionar imagem.
	public BufferedImage redimensionar(BufferedImage imagemOriginal, int tamanho) {		
		return Scalr.resize(imagemOriginal, Scalr.Method.ULTRA_QUALITY, tamanho);
	}
}
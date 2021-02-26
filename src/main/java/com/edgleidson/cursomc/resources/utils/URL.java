package com.edgleidson.cursomc.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class URL {
	
	//Método para descodificar o parâmetro que veio da URL.
	public static String decodificarParametro(String s) {
		try {
			return URLDecoder.decode(s, "UTF-8");
		} catch (UnsupportedEncodingException ex) {
			return ""; //Em caso de erro vai retornar uma String vazia.
		}
	}

	//Método converter String na lista de Inteiros.
	public static List<Integer> converterStringNaListaDeInteiro(String s){
		String[] vetor = s.split(","); 
		//split = Serve para recortar uma String de acordo com caractere(,) que foi passado.
		List<Integer> lista = new ArrayList<>();
		for(int i=0; i<vetor.length; i++) {
			lista.add(Integer.parseInt(vetor[i]));
		}
		return lista;
	}
}
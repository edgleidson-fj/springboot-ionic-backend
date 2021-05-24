package com.edgleidson.cursomc.filtro;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

//Classe de Filtro = Para expor o Header Location nas respostas.
//Implementar = (Filter) Servlet.

@Component
public class FiltroParaExporCabecalho implements Filter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletResponse resp = (HttpServletResponse) response;
		resp.addHeader("access-control-expose-headers", "location"); //Expondo cabecalho (Location). 
		chain.doFilter(request, response);
	}

}
package com.edgleidson.cursomc.domain;

import javax.persistence.Entity;

import com.edgleidson.cursomc.domain.enums.EstadoPagamento;

// SubClasse da Classe(Pagamento).

@Entity
public class PagamentoComCartao extends Pagamento{
	private static final long serialVersionUID = 1L;
	
	private Integer numeroDeParcelas;
	
	public PagamentoComCartao() {
	}

	// Construtor da Super Classe (Pagamento) + Atributo da Classe {numeroDeParcelas}.
	public PagamentoComCartao(Integer id, EstadoPagamento estado, Pedido pedido, Integer numeroDeParcelas) {
		super(id, estado, pedido);
		this.numeroDeParcelas = numeroDeParcelas;
	}

	public Integer getNumeroDeParcelas() {
		return numeroDeParcelas;
	}

	public void setNumeroDeParcelas(Integer numeroDeParcelas) {
		this.numeroDeParcelas = numeroDeParcelas;
	}
	}
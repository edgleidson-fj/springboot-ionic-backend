package com.edgleidson.cursomc.domain;

import java.util.Date;

import javax.persistence.Entity;

import com.edgleidson.cursomc.domain.enums.EstadoPagamento;

//SubClasse da Classe(Pagamento).

@Entity
public class PagamentoComBoleto extends Pagamento{
	private static final long serialVersionUID = 1L;

private Date dataVencimento;
	private Date dataPagamento;
	
	public PagamentoComBoleto() {
	}

	// Construtor da Super Classe (Pagamento) + Atributos da Classe {dataVencimento, dataPagamento}.
	public PagamentoComBoleto(Integer id, EstadoPagamento estado, Pedido pedido, Date dataVencimento, Date dataPagamento) {
		super(id, estado, pedido);
		this.dataVencimento = dataVencimento;
		this.dataPagamento = dataPagamento;
	}

	public Date getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public Date getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}	
}
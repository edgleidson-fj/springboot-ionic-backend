package com.edgleidson.cursomc.domain;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Locale;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

// Classe de associacao = Nao tem ID proprio. Quem identifica sao os 2 objetos(Produto/Pedido) associado a ela.

@Entity
public class ItemPedido implements Serializable{
	private static final long serialVersionUID = 1L;
	
	// Chave composta / Classe auxiliar.
	// @EmbeddeId = ID imbutido na Classe auxiliar (ItemPedidoPK).
	// @JsonIgnore = Nesse caso esta° ignorando a serializacao da Chave Composta.
	@JsonIgnore
	@EmbeddedId
	private ItemPedidoPK id = new ItemPedidoPK();
	
	private Double desconto;
	private Integer quantidade;
	private Double preco;
	
	public ItemPedido() {
	}

	public ItemPedido(Pedido pedido, Produto produto, Double desconto, Integer quantidade, Double preco) {
		super();
		id.setPedido(pedido); // ItemPedidoPK.
		id.setProduto(produto);// ItemPedidoPK.
		this.desconto = desconto;
		this.quantidade = quantidade;
		this.preco = preco;
	}
	
	public double getSubtotal() {
		return (preco - desconto) * quantidade;
	}	
	
	// ItemPedidoPK.
	// @JsonIgnore = Nesse caso esta ignorando a serializacao para evitar Json ciclico(Loop infinito).
	@JsonIgnore
	public Pedido getPedido() {
		return id.getPedido();
	}
	
	public void setPedido(Pedido pedido) {
		id.setPedido(pedido);
	}
	
	// ItemPedidoPK.
	public Produto getProduto() {
		return id.getProduto();
	}
	
	public void setProduto(Produto produto) {
		id.setProduto(produto);
	}

	public ItemPedidoPK getId() {
		return id;
	}

	public void setId(ItemPedidoPK id) {
		this.id = id;
	}

	public Double getDesconto() {
		return desconto;
	}

	public void setDesconto(Double desconto) {
		this.desconto = desconto;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemPedido other = (ItemPedido) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	// toString.
	@Override
	public String toString() {
		// Formatar em Dinheiro - Real(Brasileiro).
		NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt","BR"));
		
		StringBuilder builder = new StringBuilder();
		builder.append(getProduto().getNome());
		builder.append(", Qtde: ");
		builder.append(quantidade);
		builder.append(", Pre√ßo: ");
		builder.append(nf.format(preco));
		builder.append(", SubTotal: ");
		builder.append(nf.format(getSubtotal()));
		builder.append("\n");
		return builder.toString();
	}		
}
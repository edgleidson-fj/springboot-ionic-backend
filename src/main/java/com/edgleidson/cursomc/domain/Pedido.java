package com.edgleidson.cursomc.domain;

import java.io.Serializable;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Pedido implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	private Date instante;
	
	// Associação = Um PEDIDO p/ Um PAGAMENTO.
	// (mappedBy = "pedido-[Pagamento]").
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "pedido")
	private Pagamento pagamento;
	
	// Associação = Muitos PEDIDO p/ Um CLIENTE.
	@ManyToOne
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;
	
	// Associação = Muitos PEDIDO p/ Um ENDEREÇO.
	@ManyToOne
	@JoinColumn(name = "endereco_de_entrega_id")
	private Endereco enderecoDeEntrega;
	
	// Associação = Um PEDIDO p/ Muitos ITENS.
	// Chave composta (Produto/Pedido).
	// Conjunto de Itens. Obs: Pedido conhece os Itens.
	// Set<> = Para evitar valores repetidos.
	// (mappedBy = "id-[ItemPedido] _ pedido-[ItemPedidoPK]")
	@OneToMany(mappedBy = "id.pedido")
	private Set<ItemPedido> itens = new HashSet<>();
	
	public Pedido() {
	}

	public Pedido(Integer id, Date instante, Cliente cliente, Endereco enderecoDeEntrega) {
		super();
		this.id = id;
		this.instante = instante;
		this.cliente = cliente;
		this.enderecoDeEntrega = enderecoDeEntrega;
	}
	
	public double getValorTotal() {
		double soma = 0.0;
		for(ItemPedido ip : itens) {
			soma = soma + ip.getSubtotal();
		}
		return soma;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getInstante() {
		return instante;
	}

	public void setInstante(Date instante) {
		this.instante = instante;
	}

	public Pagamento getPagamento() {
		return pagamento;
	}

	public void setPagamento(Pagamento pagamento) {
		this.pagamento = pagamento;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Endereco getEnderecoDeEntrega() {
		return enderecoDeEntrega;
	}

	public void setEnderecoDeEntrega(Endereco enderecoDeEntrega) {
		this.enderecoDeEntrega = enderecoDeEntrega;
	}	

	//Chave composta (Produto/Pedido).
	public Set<ItemPedido> getItens() {
		return itens;
	}

	public void setItens(Set<ItemPedido> itens) {
		this.itens = itens;
	}
	//-----------------------------------------
	
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
		Pedido other = (Pedido) obj;
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
			// Formatar Data.
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
			
			StringBuilder builder = new StringBuilder();
			builder.append("Pedido número: ");
			builder.append(getId());
			builder.append(", Instante: ");
			builder.append(sdf.format(getInstante()));
			builder.append(", Cliente: ");
			builder.append(getCliente().getNome());
			builder.append(", Situação: ");
			builder.append(getPagamento().getEstado().getDescricao());
			builder.append("\n Detalhes \n");
			
			// Itens.
			for(ItemPedido ip : getItens()) {
				builder.append(ip.toString());
			}
			
			builder.append("Valor Total: ");
			builder.append(nf.format(getValorTotal()));
			return builder.toString();
		}		
}
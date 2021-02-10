package com.edgleidson.cursomc.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import com.edgleidson.cursomc.domain.enums.EstadoPagamento;
import com.fasterxml.jackson.annotation.JsonIgnore;

// @Inheritance = Herança.
// InheritanceType.JOINED = Uma tabela para cada SubClasse(PagamentoComBoleto - PagamentoComCartao).
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Pagamento implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;
	private Integer estado; // Enumerado (EstadoPagamento).
	
	// Associação = Um PAGAMENTO p/ Um PEDIDO.
	// @MapsId = Para definir o mesmo ID da classe mapeada(Pedido) para classe(Pagamento).
	// @JsonIgnore = Omitir a serialização do pedido do Pagamento. - Evitando Json ciclíco(Loop infínito).
	@JsonIgnore
	@OneToOne
	@JoinColumn(name = "pedido_id")
	@MapsId
	private Pedido pedido;
	
	public Pagamento() {
	}

	public Pagamento(Integer id, EstadoPagamento estado, Pedido pedido) {
		super();
		this.id = id;
		this.estado = (estado==null)? null : estado.getCodigo(); //Enumerado (EstadoPagamento).
		//Obs:Condicional Ternária = Se o estado for igual a nulo, vai atribuir nulo, caso contrário, vai atribuir o código.
		this.pedido = pedido;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	// Enumerado (EstadoPagamento).
	public EstadoPagamento getEstado() {
		return EstadoPagamento.toEnum(estado);
	}

	public void setEstado(EstadoPagamento estado) {
		this.estado = estado.getCodigo();
	}
	//------------------------------------------------
	
	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
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
		Pagamento other = (Pagamento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}	
}
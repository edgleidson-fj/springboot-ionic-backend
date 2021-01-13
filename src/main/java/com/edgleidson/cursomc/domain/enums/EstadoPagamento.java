package com.edgleidson.cursomc.domain.enums;

public enum EstadoPagamento {

	PENDENTE(1,"Pendente"),
	QUITADO(2,"Quitado"),
	CANCELADO(3,"Cancelado");
	
	private int codigo;
	private String descricao;

	// Construtor para tipo Enumerado tem que ser PRIVATE.
	private EstadoPagamento(int codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	// Tipo Enumerado só tem métodos GET.
	public int getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	// Método para recebe um código e retorna um objeto EstadoPagamento já instanciado conforme o código que for passado.
	// Static = Para ser possível executar mesmo sem instanciar objetos.
	public static EstadoPagamento toEnum(Integer codigo) {
		if (codigo == null) {
			return null;
		}

		for (EstadoPagamento x : EstadoPagamento.values()) {
			if (codigo.equals(x.getCodigo())) {
				return x;
			}
		}
		throw new IllegalArgumentException("Código inválido: " + codigo);
	}
}

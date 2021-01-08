package com.edgleidson.cursomc.domain.enums;

public enum TipoCliente {

	PESSOAFISICA(1, "Pessoa Física"), 
	PESSOAJURIDICA(2, "Pessoa Jurídica");

	private int codigo;
	private String descricao;

	// Construtor para tipo Enumerado tem que ser PRIVATE.
	private TipoCliente(int codigo, String descricao) {
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

	// Método para recebe um código e retorna um objeto TipoCliente já instanciado conforme o código que for passado.
	// Static = Para ser possível executar mesmo sem instanciar objetos.
	public static TipoCliente toEnum(Integer codigo) {
		if (codigo == null) {
			return null;
		}

		for (TipoCliente x : TipoCliente.values()) {
			if (codigo.equals(x.getCodigo())) {
				return x;
			}
		}
		throw new IllegalArgumentException("Código inválido: " + codigo);
	}
}
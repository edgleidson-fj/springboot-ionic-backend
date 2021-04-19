package com.edgleidson.cursomc.domain.enums;

public enum Perfil {

	// O prefixo "ROLE_" sao exigencia da framework Spring Security.
	ADMIN(1,"ROLE_ADMIN"), 
	CLIENTE(2,"ROLE_CLIENTE");
	
	private int codigo;
	private String descricao;

	// Construtor para tipo Enumerado tem que ser PRIVATE.
	private Perfil(int codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	// Tipo Enumerado so tem metodos GET.
	public int getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	// Metodo para recebe um codigo e retorna um objeto EstadoPagamento ja instanciado conforme o codigo que for passado.
	// Static = Para ser poss√≠vel executar mesmo sem instanciar objetos.
	public static Perfil toEnum(Integer codigo) {
		if (codigo == null) {
			return null;
		}

		for (Perfil x : Perfil.values()) {
			if (codigo.equals(x.getCodigo())) {
				return x;
			}
		}
		throw new IllegalArgumentException("CÛdigo inv·lido: " + codigo);
	}
}
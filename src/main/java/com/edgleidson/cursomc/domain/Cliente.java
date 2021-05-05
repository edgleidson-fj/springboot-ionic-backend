package com.edgleidson.cursomc.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.edgleidson.cursomc.domain.enums.Perfil;
import com.edgleidson.cursomc.domain.enums.TipoCliente;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Cliente implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	private String email;
	private String cpfOuCnpj;
	private Integer tipo; // Enumerado (TipoCliente).
	
	@JsonIgnore
	private String senha;
	
	// Associacao = Um CLIENTE p/ Muitos ENDERECO.
	// (mappedBy = "cliente-[Endereco]").
	// (cascade = "Possibilita que toda operacao que modifica o Cliente seja refletida em cascata nos Enderecos")
	// -exemplo do Cascade - Se excluir o Cliente também excluirá o Endereco dele.
	@OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
	private List<Endereco> enderecos = new ArrayList<>();
	
	// Entidade fraca.
	// @ElementCollection = Eh uma colecao de tipo (String) dependente da Entidade(Pai).
	// @CollectionTable = Criar uma tabela dependente da Entidade(Pai).
	// SET<tipo> = Para evitar valores repetidos.
	@ElementCollection
	@CollectionTable(name = "TELEFONE")
	private Set<String> telefone = new HashSet<>();
	
	//Enumerado (Perfil).
	//(EAGER) = Para buscar Perfis automaticamente, sempre que o Cliente for consultado.
	@ElementCollection(fetch=FetchType.EAGER)
	@CollectionTable(name="PERFIS")
	private Set<Integer> perfis = new HashSet<>();
	
	// Associacao = Um CLIENTE p/ Muitos PEDIDO.
	// (mappedBy = "cliente-[Pedido]").
	// @JsonIgnore = Omitir a serializacao do pedido do Pagamento. - Evitando Json ciclico(Loop infinito).
	@JsonIgnore
	@OneToMany(mappedBy = "cliente")
	private List<Pedido> pedidos = new ArrayList<>();
		
	public Cliente() {
		addPerfil(Perfil.CLIENTE);//Todo Cliente tera por padrao o Perfil(CLIENTE).
	}

	public Cliente(Integer id, String nome, String email, String cpfOuCnpj, TipoCliente tipo, String senha) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.cpfOuCnpj = cpfOuCnpj;
		this.tipo = (tipo==null)? null : tipo.getCodigo(); //Enumerado (TipoCliente).
		//Obs:Condicional Ternaria = Se o tipo for igual a nulo, vai atribuir nulo, caso contrário, vai atribuir o codigo.
		this.senha = senha;
		addPerfil(Perfil.CLIENTE);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpfOuCnpj() {
		return cpfOuCnpj;
	}

	public void setCpfOuCnpj(String cpfOuCnpj) {
		this.cpfOuCnpj = cpfOuCnpj;
	}

	//Enumerado (TipoCliente).
	public TipoCliente getTipo() {
		return TipoCliente.toEnum(tipo); //Metodo dentro do Enum.
	}

	public void setTipo(TipoCliente tipo) {
		this.tipo = tipo.getCodigo();
	}
	//------------------------------------------
	
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	//Enumerado (Perfil).
	public Set<Perfil> getPerfis(){
		return perfis.stream().map(x -> Perfil.toEnum(x)).collect(Collectors.toSet());
	}
	
	public void addPerfil(Perfil perfil) {
		perfis.add(perfil.getCodigo());
	}
	//-------------------------------------------
	
	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	public Set<String> getTelefone() {
		return telefone;
	}

	public void setTelefone(Set<String> telefone) {
		this.telefone = telefone;
	}
	
	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
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
		Cliente other = (Cliente) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	}
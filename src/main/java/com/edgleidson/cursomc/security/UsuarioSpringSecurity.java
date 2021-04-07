package com.edgleidson.cursomc.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.edgleidson.cursomc.domain.enums.Perfil;

// Implementa os métodos do (UserDetails) da framework Spring Security.

public class UsuarioSpringSecurity implements UserDetails{
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String email;
	private String senha;
	private Collection<? extends GrantedAuthority> autoridades;
	
	public UsuarioSpringSecurity() {
		}	
	
	public UsuarioSpringSecurity(Integer id, String email, String senha,Set<Perfil> perfis) {
		super();
		this.id = id;
		this.email = email;
		this.senha = senha;
		this.autoridades = perfis.stream()
				.map(x -> new SimpleGrantedAuthority(x.getDescricao())).collect(Collectors.toList());
		//Pegando a descrição do Perfil.
	}

	public Integer getId(){
		return id;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		//Obter a Coleção de autoridades/perfis de usuários.
		return autoridades;
	}

	@Override
	public String getPassword() {
		return senha;
	}

	@Override
	public String getUsername() {
		return email;
	}
	
	@Override
	public boolean isAccountNonExpired() {
		//A CONTA NÃO ESTÁ EXPIRADA.
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		//A CONTA NÃO ESTÁ BLOQUEADA.
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		//AS CREDENCIAIS NÃO ESTÃO EXPIRADAS
		return true;
	}

	@Override
	public boolean isEnabled() {
		//O USUÁRIO ESTÁ ATIVO.
		return true;
	}
	
	
	// hasRole => No código do professor Nelio Alves.
	public boolean temFuncao(Perfil perfil) {		
		return getAuthorities().contains(new SimpleGrantedAuthority(perfil.getDescricao()));
		//Retornar a descrição do Perfil.
	}
}
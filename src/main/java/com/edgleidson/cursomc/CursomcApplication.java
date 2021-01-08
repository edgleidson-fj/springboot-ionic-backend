package com.edgleidson.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

// CursoMC - Curso de Modelagem Conceitual.

// Aula 24 - Endpoint /clientes/{id} disponível.

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.edgleidson.cursomc.domain.Categoria;
import com.edgleidson.cursomc.domain.Cidade;
import com.edgleidson.cursomc.domain.Cliente;
import com.edgleidson.cursomc.domain.Endereco;
import com.edgleidson.cursomc.domain.Estado;
import com.edgleidson.cursomc.domain.Produto;
import com.edgleidson.cursomc.domain.enums.TipoCliente;
import com.edgleidson.cursomc.repository.CategoriaRepository;
import com.edgleidson.cursomc.repository.CidadeRepository;
import com.edgleidson.cursomc.repository.ClienteRepository;
import com.edgleidson.cursomc.repository.EnderecoRepository;
import com.edgleidson.cursomc.repository.EstadoRepository;
import com.edgleidson.cursomc.repository.ProdutoRepository;

//CommandLineRunner = Executar alguma função automaticamente através do método Run(). 
@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{
	
	@Autowired
	CategoriaRepository categoriaRepository;
	@Autowired
	ProdutoRepository produtoRepository;
	@Autowired
	EstadoRepository estadoRepository;
	@Autowired
	CidadeRepository cidadeRepository;
	@Autowired
	ClienteRepository clienteRepository;
	@Autowired
	EnderecoRepository enderecoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		Produto p1 = new Produto(null, "Computador", 2000.0);
		Produto p2 = new Produto(null, "Impressora", 800.0);
		Produto p3 = new Produto(null, "Mouse", 80.0);
		
		//Categoria - lista de produtos.
		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		//Produto - lista de categorias.
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1,cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		categoriaRepository.saveAll(Arrays.asList(cat1,cat2));
		produtoRepository.saveAll(Arrays.asList(p1,p2,p3));
		//-------------------------------------------------
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		//Estado - lista de cidades.
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2,c3));		
		
		estadoRepository.saveAll(Arrays.asList(est1,est2));
		cidadeRepository.saveAll(Arrays.asList(c1,c2,c3));
		//--------------------------------------------------
		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "36378912377", TipoCliente.PESSOAFISICA);
		
		//Cliente - lista de telefones.
		cli1.getTelefone().addAll(Arrays.asList("27363323","93839383"));
		
		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apt 303", "Jardim", "38220834", cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cli1, c2);
		
		//Cliente - lista de endereços.
		cli1.getEnderecos().addAll(Arrays.asList(e1,e2));
		
		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1,e2));
	}
}
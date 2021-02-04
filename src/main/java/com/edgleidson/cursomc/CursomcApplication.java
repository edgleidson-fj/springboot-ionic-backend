package com.edgleidson.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

// CursoMC - Curso de Modelagem Conceitual.

// Aula: 40 Personalizando o retorno da validação.

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.edgleidson.cursomc.domain.Categoria;
import com.edgleidson.cursomc.domain.Cidade;
import com.edgleidson.cursomc.domain.Cliente;
import com.edgleidson.cursomc.domain.Endereco;
import com.edgleidson.cursomc.domain.Estado;
import com.edgleidson.cursomc.domain.ItemPedido;
import com.edgleidson.cursomc.domain.Pagamento;
import com.edgleidson.cursomc.domain.PagamentoComBoleto;
import com.edgleidson.cursomc.domain.PagamentoComCartao;
import com.edgleidson.cursomc.domain.Pedido;
import com.edgleidson.cursomc.domain.Produto;
import com.edgleidson.cursomc.domain.enums.EstadoPagamento;
import com.edgleidson.cursomc.domain.enums.TipoCliente;
import com.edgleidson.cursomc.repository.CategoriaRepository;
import com.edgleidson.cursomc.repository.CidadeRepository;
import com.edgleidson.cursomc.repository.ClienteRepository;
import com.edgleidson.cursomc.repository.EnderecoRepository;
import com.edgleidson.cursomc.repository.EstadoRepository;
import com.edgleidson.cursomc.repository.ItemPedidoRepository;
import com.edgleidson.cursomc.repository.PagamentoRepository;
import com.edgleidson.cursomc.repository.PedidoRepository;
import com.edgleidson.cursomc.repository.ProdutoRepository;

//CommandLineRunner = Executar alguma função automaticamente através do método Run(). 
@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private PagamentoRepository pagamentoRepository;
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		Categoria cat3 = new Categoria(null, "Cama, mesa e banho");
		Categoria cat4 = new Categoria(null, "Eletrônicos");
		Categoria cat5 = new Categoria(null, "Jardinagem");
		Categoria cat6 = new Categoria(null, "Decoração");
		Categoria cat7 = new Categoria(null, "Perfumaria");
		
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
		
		categoriaRepository.saveAll(Arrays.asList(cat1,cat2,cat3,cat4,cat5,cat6,cat7));
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
		//-----------------------------------------------------
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm"); 
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cli1, e2);
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"),null);
		
		//Pedido - um pagamento.
		ped1.setPagamento(pagto1);
		ped2.setPagamento(pagto2);
		
		//Cliente - lista de pedidos.
		cli1.getPedidos().addAll(Arrays.asList(ped1,ped2));
		
		pedidoRepository.saveAll(Arrays.asList(ped1,ped2));
		pagamentoRepository.saveAll(Arrays.asList(pagto1,pagto2)); 
		//--------------------------------------------------------
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);
		
		//Pedido - Lista de Itens(Produto).
		ped1.getItens().addAll(Arrays.asList(ip1,ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		//Produto - Lista de ItemPedido(Pedido).
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));
		
		itemPedidoRepository.saveAll(Arrays.asList(ip1,ip2,ip3));
	}
}
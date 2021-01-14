package com.edgleidson.cursomc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edgleidson.cursomc.domain.ItemPedido;

@Repository                                          
public interface ItemPedidoRepository  extends JpaRepository<ItemPedido, Integer>{
}
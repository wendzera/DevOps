package br.com.examplefatec.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.examplefatec.entity.ItemDoPedido;

public interface ItemDoPedidoRepository extends JpaRepository<ItemDoPedido ,Integer>{
    
}

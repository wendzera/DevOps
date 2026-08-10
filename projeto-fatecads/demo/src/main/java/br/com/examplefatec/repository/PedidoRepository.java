package br.com.examplefatec.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.examplefatec.entity.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Integer>{
    
}

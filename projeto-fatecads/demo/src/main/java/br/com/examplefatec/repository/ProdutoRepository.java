package br.com.examplefatec.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.examplefatec.entity.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {


    
}

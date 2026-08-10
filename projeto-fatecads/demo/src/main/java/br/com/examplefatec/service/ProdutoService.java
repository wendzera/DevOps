package br.com.examplefatec.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.examplefatec.entity.Produto;
import br.com.examplefatec.repository.ProdutoRepository;

@Service
public class ProdutoService {
    
    @Autowired
    private ProdutoRepository produtoRepository;

    //método para salvar um produto
    public Produto save(Produto produto){
        return produtoRepository.save(produto);


    }
    //método para buscar todos os produtos
    public Produto findById(Integer id){
        return produtoRepository.findById(id).orElse(other:null);
    }

    //metodo para excluir um produto pelo id
    public void deleteById(Integer id){
        produtoRepository.deleteById(id);
    }
}

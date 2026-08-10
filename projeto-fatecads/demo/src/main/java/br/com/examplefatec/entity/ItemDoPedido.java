package br.com.examplefatec.entity;

import org.hibernate.annotations.ManyToAny;
import org.hibernate.mapping.ManyToOne;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;


public class ItemDoPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idItem;

    private Integer quantidade;

    private Double preco;

    private Double subtotal;


    @jakarta.persistence.ManyToOne
    @JoinColumn(name = "idPedido_fk")
    private Pedido pedido;


    @jakarta.persistence.ManyToOne
    @JoinColumn(name = "idProduto_fk")
    private Produto produto;

    //método para calcular o subtotal
    public Double calcularSubtotal(){
        return quantidade * preco;
    }
    //método para atualizar subtotal
    public void atualizarSubtotal(){
        this.subtotal = calcularSubtotal();
    }
    public Double getSubtotal() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSubtotal'");
    }
    

}

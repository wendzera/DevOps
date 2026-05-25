package br.com.examplefatec.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idUsuario;

    @Column(nullable = false, length = 80)
    private String nomeUsuario;

    @Column(nullable = false, unique = true, length = 100)
    private String emailUsuario;

    @Column(nullable = false, length = 100)
    private String senhaUsuario;

    @Column(length = 20)
    private String tokenRecuperacaoSenha;

    private LocalDateTime tokenRecuperacaoExpiracao;
}

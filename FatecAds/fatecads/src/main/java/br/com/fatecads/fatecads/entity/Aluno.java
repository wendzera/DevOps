package br.com.fatecads.fatecads.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Integer idAluno;

    @Column(nullable = false, length = 40)
    private String nomeAluno;

    @Column (length = 100)
    private String emailAluno;

    @Column (nullable = false, length = 11)
    private String telefoneAluno;

    @Column (nullable = false, length = 50)
    private String enderecoAluno;

    @Column (nullable = false, length = 11)
    private String cpfAluno;

    @Column (nullable = false)
    private String raAluno;

    public Integer getIdAluno() {
        return idAluno;
    }
    public void setIdAluno(Integer idAluno) {
        this.idAluno = idAluno;
    }
    public String getNomeAluno() {
        return nomeAluno;
    }
    public void setNomeAluno(String nomeAluno) {
        this.nomeAluno = nomeAluno;
    }
    public String getEmailAluno() {
        return emailAluno;
    }
    public void setEmailAluno(String emailAluno) {
        this.emailAluno = emailAluno;
    }
    public String getTelefoneAluno() {
        return telefoneAluno;
    }
    public void setTelefoneAluno(String telefoneAluno) {
        this.telefoneAluno = telefoneAluno;
    }
    public String getEnderecoAluno() {
        return enderecoAluno;
    }
    public void setEnderecoAluno(String enderecoAluno) {
        this.enderecoAluno = enderecoAluno;
    }
    public String getCpfAluno() {
        return cpfAluno;
    }
    public void setCpfAluno(String cpfAluno) {
        this.cpfAluno = cpfAluno;
    }
    public String getRaAluno() {
        return raAluno;
    }
    public void setRaAluno(String raAluno) {
        this.raAluno = raAluno;
    }
    
}

package br.Services;

import java.util.List;

import br.com.examplefatec.entity.Aluno;
import br.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlunoService {
    // injecao de dependencias do repositorio aluno
    @Autowired
    private AlunoRepository alunoRepository;

    // metodo para salvar o aluno
    public Aluno save(Aluno aluno) {
        return alunoRepository.save(aluno);
    }
    
    // metodo para listar todos os alunos
    public List<Aluno> findAll() {
        return alunoRepository.findAll();
    }
}

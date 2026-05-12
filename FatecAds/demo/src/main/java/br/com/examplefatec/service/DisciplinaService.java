package br.com.examplefatec.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.examplefatec.entity.Disciplina;
import br.com.examplefatec.repository.DisciplinaRepository;

@Service
public class DisciplinaService {

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    public Disciplina save(Disciplina disciplina) {
        return disciplinaRepository.save(disciplina);
    }

    public List<Disciplina> findAll() {
        return disciplinaRepository.findAll();
    }

    public Disciplina findById(Integer id) {
        return disciplinaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Disciplina nao encontrada: " + id));
    }

    public void deleteById(Integer id) {
        disciplinaRepository.deleteById(id);
    }
}

package br.com.examplefatec.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.examplefatec.entity.Professor;
import br.com.examplefatec.repository.ProfessorRepository;

@Service
public class ProfessorService {

    @Autowired
    private ProfessorRepository professorRepository;

    public Professor save(Professor professor) {
        return professorRepository.save(professor);
    }

    public List<Professor> findAll() {
        return professorRepository.findAll();
    }

    public Professor findById(Integer id) {
        return professorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Professor nao encontrado: " + id));
    }

    public void deleteById(Integer id) {
        professorRepository.deleteById(id);
    }
}

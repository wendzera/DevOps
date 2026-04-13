package br.com.examplefatec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.examplefatec.entity.Professor;
import br.com.examplefatec.service.ProfessorService;

@Controller
@RequestMapping("/professores")
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Professor professor) {
        professorService.save(professor);
        return "redirect:/professores/listar";
    }

    @GetMapping("/listar")
    public String listar(Model model) {
        model.addAttribute("professores", professorService.findAll());
        return "professor/listarProfessores";
    }

    @GetMapping("/criar")
    public String criar(Model model) {
        model.addAttribute("professor", new Professor());
        return "professor/formularioProfessor";
    }
}

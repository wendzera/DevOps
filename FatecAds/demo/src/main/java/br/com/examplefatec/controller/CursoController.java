package br.com.examplefatec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.examplefatec.entity.Curso;
import br.com.examplefatec.service.CursoService;

@Controller
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Curso curso) {
        cursoService.save(curso);
        return "redirect:/cursos/listar";
    }

    @GetMapping("/listar")
    public String listar(Model model) {
        model.addAttribute("cursos", cursoService.findAll());
        return "curso/listarCursos";
    }

    @GetMapping("/criar")
    public String criar(Model model) {
        model.addAttribute("curso", new Curso());
        return "curso/formularioCurso";
    }
}

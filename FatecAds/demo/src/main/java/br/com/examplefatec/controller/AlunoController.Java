package br.com.fatecads.fatecads.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.requestMapping;

import br.com.examplefatec.entity.AlunoService;

@controller
@requestMapping("/alunos")
public class AlunoController {
    @Autowired
    private AlunoService alunoService;

 
 
    // metodo para salvar aluno.
    @postMapping("/salvar")
    public String salvar(@ModelAttribute Aluno aluno) {
        alunoService.save(aluno);
        return "redirect:/alunos/listar";
    }

    //metodo para listar todos os alunos.
    @getmapping("/listar")
    public String listar(Model model) {
        model.addAttribute("alunos", alunoService.findAll());
        return "alunos/listaAlunos";
    }


}


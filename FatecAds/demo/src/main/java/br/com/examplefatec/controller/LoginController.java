package br.com.examplefatec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.examplefatec.entity.Usuario;
import br.com.examplefatec.service.UsuarioService;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error,
            @RequestParam(required = false) String logout,
            Model model) {
        if (error != null) {
            model.addAttribute("erro", "E-mail ou senha invalidos.");
        }
        if (logout != null) {
            model.addAttribute("mensagem", "Logout realizado com sucesso.");
        }
        return "usuario/login";
    }

    @PostMapping("/login")
    public String autenticar(@RequestParam String emailUsuario,
            @RequestParam String senhaUsuario,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        return usuarioService.autenticar(emailUsuario, senhaUsuario)
                .map(usuario -> {
                    session.setAttribute("usuarioLogado", usuario);
                    return "redirect:/";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("erro", "E-mail ou senha invalidos.");
                    return "redirect:/login";
                });
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/recuperar-senha")
    public String recuperarSenha() {
        return "usuario/recuperarSenha";
    }

    @PostMapping("/recuperar-senha")
    public String solicitarRecuperacaoSenha(@RequestParam String emailUsuario,
            RedirectAttributes redirectAttributes) {
        try {
            usuarioService.solicitarRecuperacaoSenha(emailUsuario);
            redirectAttributes.addFlashAttribute("mensagem",
                    "Se o e-mail estiver cadastrado, voce recebera um token para redefinir a senha.");
        } catch (Exception exception) {
            redirectAttributes.addFlashAttribute("erro",
                    "Nao foi possivel enviar o e-mail de recuperacao. Verifique a configuracao SMTP.");
        }
        return "redirect:/recuperar-senha";
    }

    @GetMapping("/redefinir-senha")
    public String redefinirSenha(@RequestParam(required = false) String token, Model model) {
        model.addAttribute("token", token);
        if (token != null && !token.isBlank() && !usuarioService.tokenRecuperacaoValido(token)) {
            model.addAttribute("erro", "Token invalido ou expirado.");
        }
        return "usuario/redefinirSenha";
    }

    @PostMapping("/redefinir-senha")
    public String salvarNovaSenha(@RequestParam String token,
            @RequestParam String novaSenha,
            @RequestParam String confirmarSenha,
            RedirectAttributes redirectAttributes) {
        if (!novaSenha.equals(confirmarSenha)) {
            redirectAttributes.addFlashAttribute("erro", "As senhas nao conferem.");
            redirectAttributes.addAttribute("token", token);
            return "redirect:/redefinir-senha";
        }

        try {
            usuarioService.redefinirSenha(token, novaSenha);
            redirectAttributes.addFlashAttribute("mensagem",
                    "Senha redefinida com sucesso. Faca login com a nova senha.");
            return "redirect:/login";
        } catch (IllegalArgumentException exception) {
            redirectAttributes.addFlashAttribute("erro", exception.getMessage());
            redirectAttributes.addAttribute("token", token);
            return "redirect:/redefinir-senha";
        }
    }

    @GetMapping("/usuarios/listar")
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioService.findAll());
        return "usuario/listarUsuarios";
    }

    @GetMapping("/usuarios/criar")
    public String criarUsuario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuario/FormularioUsuario";
    }

    @PostMapping("/usuarios/salvar")
    public String salvarUsuario(@ModelAttribute Usuario usuario) {
        usuarioService.save(usuario);
        return "redirect:/usuarios/listar";
    }

    @GetMapping("/usuarios/editar/{id}")
    public String editarUsuario(@PathVariable Integer id, Model model) {
        model.addAttribute("usuario", usuarioService.findById(id));
        return "usuario/FormularioUsuario";
    }

    @GetMapping("/usuarios/excluir/{id}")
    public String excluirUsuario(@PathVariable Integer id) {
        usuarioService.deleteById(id);
        return "redirect:/usuarios/listar";
    }
}

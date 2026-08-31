package br.com.fatecads.fatecads.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.fatecads.fatecads.entity.Produto;
import br.com.fatecads.fatecads.service.ProdutoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    // Método para listar todos os produtos
    @GetMapping("/listar")
    public String listar(Model model) {
        List<Produto> produtos = produtoService.findAll();
        model.addAttribute("produtos", produtos);
        return "produto/listarProdutos";
    }

    // Método para abrir o formulário de criação de produtos
    @GetMapping("/criar")
    public String criarForm(Model model) {
        model.addAttribute("produto", new Produto());
        return "produto/formularioProduto";
    }
    
    // Método para salvar o produto no banco de dados
    @PostMapping(value = "/salvar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String salvar(@ModelAttribute Produto produto,
            @RequestParam(name = "imagem", required = false) MultipartFile imagem,
            Model model) {
        try {
            produtoService.save(produto, imagem);
            return "redirect:/produtos/listar";
        } catch (IllegalArgumentException | IOException e) {
            model.addAttribute("erro", e.getMessage());
            return "produto/formularioProduto";
        }
    }
    
    // Método para abrir o formulário de edição de produtos
    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Integer id, Model model) {
        Produto produto = produtoService.findById(id);
        if (produto == null) {
            return "redirect:/produtos/listar";
        }
        model.addAttribute("produto", produto);
        return "produto/formularioProduto";
    }

    @GetMapping("/imagem/{id}")
    public ResponseEntity<byte[]> exibirImagem(@PathVariable Integer id) {
        Produto produto = produtoService.findById(id);
        if (produto == null || produto.getImagemProduto() == null || produto.getImagemProduto().length == 0) {
            return ResponseEntity.notFound().build();
        }

        MediaType tipo = MediaType.APPLICATION_OCTET_STREAM;
        if (produto.getTipoImagemProduto() != null) {
            try {
                tipo = MediaType.parseMediaType(produto.getTipoImagemProduto());
            } catch (IllegalArgumentException ignored) {
                // Usa o tipo genérico quando o tipo salvo não for reconhecido.
            }
        }

        return ResponseEntity.ok()
                .contentType(tipo)
                .cacheControl(CacheControl.noCache())
                .body(produto.getImagemProduto());
    }
    
    // Método para excluir um produto pelo ID
    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Integer id) {
        produtoService.deleteById(id);
        return "redirect:/produtos/listar";
    }
    
    
}

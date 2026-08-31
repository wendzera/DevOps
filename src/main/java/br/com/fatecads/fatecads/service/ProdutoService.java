package br.com.fatecads.fatecads.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.fatecads.fatecads.entity.Produto;
import br.com.fatecads.fatecads.repository.ProdutoRepository;

@Service
public class ProdutoService {

    private static final long TAMANHO_MAXIMO_IMAGEM = 5 * 1024 * 1024;

    @Autowired
    private ProdutoRepository produtoRepository;

    public Produto save(Produto produto, MultipartFile imagem) throws IOException {
        validar(produto);

        Produto produtoParaSalvar = produto;
        if (produto.getIdProduto() != null) {
            produtoParaSalvar = produtoRepository.findById(produto.getIdProduto())
                    .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado."));
            produtoParaSalvar.setDescricaoProduto(produto.getDescricaoProduto().trim());
            produtoParaSalvar.setValorProduto(produto.getValorProduto());
            produtoParaSalvar.setUnidadeProduto(produto.getUnidadeProduto().trim());
            produtoParaSalvar.setMarcaProduto(produto.getMarcaProduto().trim());
            produtoParaSalvar.setQuantidadeProduto(produto.getQuantidadeProduto());
        } else {
            produtoParaSalvar.setDescricaoProduto(produto.getDescricaoProduto().trim());
            produtoParaSalvar.setUnidadeProduto(produto.getUnidadeProduto().trim());
            produtoParaSalvar.setMarcaProduto(produto.getMarcaProduto().trim());
        }

        if (imagem != null && !imagem.isEmpty()) {
            validarImagem(imagem);
            produtoParaSalvar.setImagemProduto(imagem.getBytes());
            produtoParaSalvar.setTipoImagemProduto(imagem.getContentType());
            produtoParaSalvar.setNomeImagemProduto(imagem.getOriginalFilename());
        }

        return produtoRepository.save(produtoParaSalvar);
    }

    // Método para listar todos os produtos
    public List<Produto> findAll() {
        return produtoRepository.findAll();
    }

    // Método para buscar todos os produtos
    public Produto findById(Integer id) {
        return produtoRepository.findById(id).orElse(null);
    }

    public void deleteById(Integer id) {
        produtoRepository.deleteById(id);
    }

    private void validar(Produto produto) {
        if (produto.getDescricaoProduto() == null || produto.getDescricaoProduto().isBlank()) {
            throw new IllegalArgumentException("Informe a descrição do produto.");
        }
        if (produto.getValorProduto() == null || produto.getValorProduto() < 0) {
            throw new IllegalArgumentException("Informe um valor válido para o produto.");
        }
        if (produto.getUnidadeProduto() == null || produto.getUnidadeProduto().isBlank()) {
            throw new IllegalArgumentException("Informe a unidade do produto.");
        }
        if (produto.getMarcaProduto() == null || produto.getMarcaProduto().isBlank()) {
            throw new IllegalArgumentException("Informe a marca do produto.");
        }
        if (produto.getQuantidadeProduto() == null || produto.getQuantidadeProduto() < 0) {
            throw new IllegalArgumentException("A quantidade deve ser zero ou maior.");
        }
    }

    private void validarImagem(MultipartFile imagem) {
        if (imagem.getSize() > TAMANHO_MAXIMO_IMAGEM) {
            throw new IllegalArgumentException("A imagem deve ter no máximo 5 MB.");
        }
        if (imagem.getContentType() == null || !imagem.getContentType().startsWith("image/")) {
            throw new IllegalArgumentException("O arquivo enviado deve ser uma imagem.");
        }
    }
}

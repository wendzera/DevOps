package br.com.fatecads.fatecads.service;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import br.com.fatecads.fatecads.entity.Produto;

@SpringBootTest
@Transactional
class ProdutoServiceTests {

    @Autowired
    private ProdutoService produtoService;

    @Test
    void deveEditarQuantidadeSemApagarImagemExistente() throws Exception {
        byte[] conteudoImagem = new byte[] { 1, 2, 3, 4 };
        MockMultipartFile imagem = new MockMultipartFile(
                "imagem", "produto.png", "image/png", conteudoImagem);

        Produto salvo = produtoService.save(novoProduto(10), imagem);

        Produto alteracao = novoProduto(25);
        alteracao.setIdProduto(salvo.getIdProduto());
        alteracao.setDescricaoProduto("Produto editado");
        Produto editado = produtoService.save(alteracao, null);

        assertEquals(25, editado.getQuantidadeProduto());
        assertEquals("Produto editado", editado.getDescricaoProduto());
        assertEquals("produto.png", editado.getNomeImagemProduto());
        assertEquals("image/png", editado.getTipoImagemProduto());
        assertArrayEquals(conteudoImagem, editado.getImagemProduto());
    }

    @Test
    void deveRejeitarArquivoQueNaoSejaImagem() {
        MockMultipartFile arquivo = new MockMultipartFile(
                "imagem", "produto.txt", "text/plain", new byte[] { 1 });

        IllegalArgumentException erro = assertThrows(
                IllegalArgumentException.class,
                () -> produtoService.save(novoProduto(1), arquivo));

        assertEquals("O arquivo enviado deve ser uma imagem.", erro.getMessage());
    }

    @Test
    void deveRejeitarQuantidadeNegativa() {
        IllegalArgumentException erro = assertThrows(
                IllegalArgumentException.class,
                () -> produtoService.save(novoProduto(-1), null));

        assertEquals("A quantidade deve ser zero ou maior.", erro.getMessage());
    }

    private Produto novoProduto(int quantidade) {
        Produto produto = new Produto();
        produto.setDescricaoProduto("Produto de teste");
        produto.setValorProduto(19.90);
        produto.setUnidadeProduto("UN");
        produto.setMarcaProduto("Marca teste");
        produto.setQuantidadeProduto(quantidade);
        return produto;
    }
}

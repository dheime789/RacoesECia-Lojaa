package br.com.racoesecia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository repository;

    // 1. LISTAR TUDO
    @GetMapping
    public List<Produto> listarTodos() {
        return repository.findAll();
    }

    // 2. SALVAR PRODUTO
    @PostMapping
    public Produto criar(@RequestBody Produto produto) {
        return repository.save(produto);
    }

    // 3. DELETAR
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        repository.deleteById(id);
    }

    // 4. BUSCA INTELIGENTE (IA) COM BOTÃO DE COMPRA 🛒
    @GetMapping("/buscar")
    public String buscarPorNome(@RequestParam String nome) {

        Produto produto = repository.findFirstByNomeComercialContainingIgnoreCase(nome);

        if (produto != null) {
            // AQUI ESTÁ A MÁGICA: Criamos o botão HTML dentro do Java
            // Esse botão chama a função 'abrirPedido' que já existe no seu site
            String botaoCompra = "<br><button onclick=\"abrirPedido('" + produto.getNomeComercial() + "', '" + produto.getPreco() + "')\" " +
                    "style=\"background-color:#25D366; color:white; border:none; padding:10px 20px; margin-top:10px; border-radius:20px; cursor:pointer; font-weight:bold; width:100%; box-shadow: 0 2px 5px rgba(0,0,0,0.2);\">" +
                    "🛒 COMPRAR AGORA</button>";

            return "✅ Encontrei: <b>" + produto.getNomeComercial() + "</b><br>" +
                    "💰 Preço: R$ " + produto.getPreco() + "<br>" +
                    "📦 Estoque: " + produto.getEstoque() + " un" +
                    botaoCompra;
        } else {
            return "❌ Poxa, não encontrei nada parecido com '" + nome + "'. Tente digitar apenas uma parte do nome (ex: 'sache').";
        }
    }
}
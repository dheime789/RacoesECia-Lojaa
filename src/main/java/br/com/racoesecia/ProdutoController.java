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

    // 4. BUSCA INTELIGENTE (IA) 🧠
    // Teste no navegador: http://localhost:8080/produtos/buscar?nome=pedigre
    @GetMapping("/buscar")
    public String buscarPorNome(@RequestParam String nome) {
        // Aqui chamamos o método novo do Repository!
        Produto produto = repository.buscarProdutoInteligente(nome);

        if (produto != null) {
            return "✅ Encontrei: " + produto.getNomeComercial() +
                    " | 💰 Preço: R$ " + produto.getPreco() +
                    " | 📦 Estoque: " + produto.getEstoque();
        } else {
            return "❌ Poxa, não encontrei nada parecido com '" + nome + "'.";
        }
    }
}
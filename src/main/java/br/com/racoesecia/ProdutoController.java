package br.com.racoesecia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.text.Normalizer; // Ferramenta para mexer em textos
import java.util.regex.Pattern;
import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository repository;

    // --- FUNÇÃO AJUDANTE: Remove acentos (Ex: "Ração" vira "Racao") ---
    private String removerAcentos(String texto) {
        if (texto == null) return null;
        String normalizado = Normalizer.normalize(texto, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalizado).replaceAll("");
    }

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

    // 4. BUSCA INTELIGENTE (IA) COM CORREÇÃO DE ACENTOS 🧠✅
    @GetMapping("/buscar")
    public String buscarPorNome(@RequestParam String nome) {

        // TENTATIVA 1: Busca exatamente como o cliente digitou (Ex: "Ração")
        Produto produto = repository.findFirstByNomeComercialContainingIgnoreCase(nome);

        // TENTATIVA 2: Se não achou, remove os acentos e tenta de novo (Ex: busca "Racao")
        if (produto == null) {
            String nomeSemAcento = removerAcentos(nome);
            produto = repository.findFirstByNomeComercialContainingIgnoreCase(nomeSemAcento);
        }

        if (produto != null) {
            String botaoCompra = "<br><button onclick=\"abrirPedido('" + produto.getNomeComercial() + "', '" + produto.getPreco() + "')\" " +
                    "style=\"background-color:#25D366; color:white; border:none; padding:10px 20px; margin-top:10px; border-radius:20px; cursor:pointer; font-weight:bold; width:100%; box-shadow: 0 2px 5px rgba(0,0,0,0.2);\">" +
                    "🛒 COMPRAR AGORA</button>";

            return "✅ Encontrei: <b>" + produto.getNomeComercial() + "</b><br>" +
                    "💰 Preço: R$ " + produto.getPreco() + "<br>" +
                    "📦 Estoque: " + produto.getEstoque() + " un" +
                    botaoCompra;
        } else {
            return "❌ Poxa, não encontrei nada parecido com '" + nome + "'. Tente simplificar a busca.";
        }
    }
}
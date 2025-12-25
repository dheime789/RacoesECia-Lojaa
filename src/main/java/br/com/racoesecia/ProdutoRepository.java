package br.com.racoesecia;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    // Busca Simples (Listar tudo ou filtrar normal)
    List<Produto> findByNomeComercialContainingIgnoreCase(String nome);

    // --- BUSCA INTELIGENTE (IA) 🧠 ---
    // Essa é a mágica que conserta erros de digitação (Ex: "pedigre" -> "Pedigree")
    @Query(value = "SELECT * FROM produtos " +
            "WHERE SIMILARITY(unaccent(nome_comercial::text), unaccent(:nome)) > 0.1 " +
            "ORDER BY SIMILARITY(unaccent(nome_comercial::text), unaccent(:nome)) DESC " +
            "LIMIT 1", nativeQuery = true)
    Produto buscarProdutoInteligente(@Param("nome") String nome);
}
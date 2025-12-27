package br.com.racoesecia;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    // Aqui ensinamos ao Java o que é a "Busca Inteligente"
    // O comando diz: "Selecione o produto onde o nome parece com o que foi digitado, ignorando maiúsculas"
    @Query(value = "SELECT * FROM produto WHERE LOWER(nome_comercial) LIKE LOWER(CONCAT('%', :nome, '%')) LIMIT 1", nativeQuery = true)
    Produto buscarProdutoInteligente(@Param("nome") String nome);

}
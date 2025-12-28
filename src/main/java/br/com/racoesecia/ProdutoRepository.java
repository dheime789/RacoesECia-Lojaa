package br.com.racoesecia;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    // A MÁGICA: O Spring lê esse nome e cria o SQL sozinho!
    // Tradução: "Encontre o Primeiro produto onde o NomeComercial contém esse texto (ignorando maiúsculas)"
    Produto findFirstByNomeComercialContainingIgnoreCase(String nome);

}
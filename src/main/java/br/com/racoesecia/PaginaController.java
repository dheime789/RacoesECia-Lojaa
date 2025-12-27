package br.com.racoesecia;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // <--- Atenção: Aqui é @Controller (sem o Rest), pois retorna HTML
public class PaginaController {

    // 1. Quando alguém entrar no site (Raiz), mostra a LOJA
    @GetMapping("/")
    public String carregarLoja() {
        return "loja"; // Vai abrir o arquivo loja.html
    }

    // 2. Quando digitar /admin ou /cadastro, mostra o DASHBOARD
    @GetMapping("/admin")
    public String carregarDashboard() {
        return "dashboard"; // Vai abrir o arquivo dashboard.html
    }
}
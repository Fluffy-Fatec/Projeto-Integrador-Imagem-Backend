package com.imagem.backend.infra.ext;

import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class BlacklistFactory {


    public void salvar(Integer id) {
        String url = "http://localhost:8081/blacklist";

        // Criando um objeto que representa o corpo da requisição em formato JSON
        String requestBody = "{\"id\": \"" + id + "\"}";

        // Configurando o cabeçalho da requisição
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Configurando a requisição
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        // Criando o RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // Enviando a requisição POST
        ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);

        // Verificando a resposta
        if (response.getStatusCode() == HttpStatus.OK) {
            System.out.println("Adicionado à lista negra com sucesso!");
        } else {
            System.out.println("Falha ao adicionar à lista negra. Código de status: " + response.getStatusCodeValue());
        }
    }
}
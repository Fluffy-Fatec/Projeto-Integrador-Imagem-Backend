package com.imagem.backend.infra.ext;

import com.imagem.backend.dtos.LogSender;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import com.imagem.backend.dtos.LogSender;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;

@Log4j2
@RequiredArgsConstructor
@Service
public class LogProducerService {

    private static final String API_URL = "http://localhost:8081/log";

    @SneakyThrows
    public void sendMessage(LogSender logSender){

        // Converte o objeto LogSender para JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(logSender);

        // Cria a requisição POST
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .header("Content-Type", "application/json")
                .build();

        // Envia a requisição e captura a resposta
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Exibe o status da resposta
        System.out.println("Status da requisição: " + response.statusCode());

        // Verifica se a requisição foi bem-sucedida
        if (response.statusCode() == 200) {
            System.out.println("Mensagem de log enviada com sucesso!");
        } else {
            System.out.println("Erro ao enviar mensagem de log: " + response.body());
        }
    }
}

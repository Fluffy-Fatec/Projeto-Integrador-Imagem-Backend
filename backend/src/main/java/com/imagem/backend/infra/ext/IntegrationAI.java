package com.imagem.backend.infra.ext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imagem.backend.dtos.SentimentResponse;
import com.imagem.backend.exceptions.ErrorSentiment;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Log4j2
@RequiredArgsConstructor
@Service
public class IntegrationAI {

    private static final String API_URL = "http://localhost:8082/predict?text=";

    public String getSentiment(String sentiment) {
        try {
            // Cria a requisição GET
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL + sentiment))
                    .GET()
                    .build();

            // Envia a requisição e captura a resposta
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper mapper = new ObjectMapper();
            SentimentResponse sentimentResponse = mapper.readValue(response.body(), SentimentResponse.class);

            System.out.println("sentimento "+sentimentResponse.getPrediction());
            return sentimentResponse.getPrediction();
        } catch (Exception e) {
            log.error("Erro ao fazer a requisição para obter o sentimento", e);
            throw new ErrorSentiment();
        }
    }
}

package com.imagem.backend.infra.ext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imagem.backend.dtos.LogSender;
import com.imagem.backend.dtos.LogsGroupByDay;
import com.imagem.backend.dtos.SentimentResponse;
import com.imagem.backend.exceptions.ErrorSentiment;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Service
public class IntegrationAI {

    private static final String API_URL = "http://localhost:8082/predict?text=";

    private static final String API_BASEURL = "http://localhost:8081";

    public String getSentiment(String sentiment) {
        try {
            String encodedSentiment = Base64.getUrlEncoder().encodeToString(sentiment.getBytes(StandardCharsets.UTF_8));

            // Cria a requisição GET
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL + encodedSentiment))
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


    public List<LogSender> getAccesTotal() {
        try {
            // Cria a requisição GET
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_BASEURL + "/logged/all"))
                    .GET()
                    .build();

            // Envia a requisição e captura a resposta
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("quantidade "+response);
            ObjectMapper mapper = new ObjectMapper();
            List<LogSender> sentimentResponse = mapper.readValue(response.body(), List.class);

            System.out.println("quantidade "+sentimentResponse);
            return sentimentResponse;
        } catch (Exception e) {
            log.error("Erro ao fazer a requisição para obter o sentimento", e);
            throw new ErrorSentiment();
        }
    }

    public Integer getLogToday() {
        try {
            // Cria a requisição GET
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_BASEURL + "/log/count"))
                    .GET()
                    .build();

            // Envia a requisição e captura a resposta
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("quantidade "+response);
            ObjectMapper mapper = new ObjectMapper();
            Integer sentimentResponse = mapper.readValue(response.body(), Integer.class);

            System.out.println("quantidade "+sentimentResponse);
            return sentimentResponse;
        } catch (Exception e) {
            log.error("Erro ao fazer a requisição para obter o sentimento", e);
            throw new ErrorSentiment();
        }
    }

    public List<LogsGroupByDay> getLogNewUser() {
        try {
            // Cria a requisição GET
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_BASEURL + "/log/group"))
                    .GET()
                    .build();

            // Envia a requisição e captura a resposta
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("quantidade "+response);
            ObjectMapper mapper = new ObjectMapper();
            List<LogsGroupByDay> sentimentResponse = mapper.readValue(response.body(), List.class);

            System.out.println("quantidade "+sentimentResponse);
            return sentimentResponse;
        } catch (Exception e) {
            log.error("Erro ao fazer a requisição para obter o sentimento", e);
            throw new ErrorSentiment();
        }
    }
}

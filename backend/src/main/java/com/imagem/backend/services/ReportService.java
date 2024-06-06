package com.imagem.backend.services;

import com.imagem.backend.domain.Review;
import com.imagem.backend.domain.User;
import com.imagem.backend.dtos.LogSender;
import com.imagem.backend.dtos.UserLog;
import com.imagem.backend.infra.ext.LogProducerService;
import com.imagem.backend.infra.security.UserSession;
import com.imagem.backend.repositories.ReviewRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class ReportService extends LogProducerService {
    private final ReviewRepository reviewRepository;

    private final UserSession userSession;

    public ReportService(ReviewRepository reviewRepository, UserSession userSession) {
        this.reviewRepository = reviewRepository;
        this.userSession = userSession;
    }

    public List<Review> listReviewByFilters(String origin, String startDateString, String endDateString, String state, String country, String sentimentoPredito) {
        if (origin == null || origin.isEmpty()) {
            throw new IllegalArgumentException("Datasource is a mandatory parameter.");
        }

        Timestamp startDate = null;
        Timestamp endDate = null;
        if (startDateString != null && !startDateString.isEmpty()) {
            startDate = parseDateStringToTimestamp(startDateString);
        }
        if (endDateString != null && !endDateString.isEmpty()) {
            endDate = parseDateStringToTimestamp(endDateString);
        }


        if (startDate != null && endDate != null && state != null && !state.isEmpty() && country != null && !country.isEmpty() && sentimentoPredito != null && !sentimentoPredito.isEmpty()) {
            return reviewRepository.findByOriginAndReviewCreationDateBetweenAndGeolocationStateAndGeolocationCountryAndSentimentoPredito(origin, startDate, endDate, state, country, sentimentoPredito);
        } else if (startDate != null && endDate != null && state != null && !state.isEmpty() && country != null && !country.isEmpty()) {
            return reviewRepository.findByOriginAndReviewCreationDateBetweenAndGeolocationStateAndGeolocationCountry(origin, startDate, endDate, state, country);
        } else if (startDate != null && endDate != null && state != null && !state.isEmpty() && sentimentoPredito != null && !sentimentoPredito.isEmpty()) {
            return reviewRepository.findByOriginAndReviewCreationDateBetweenAndGeolocationStateAndSentimentoPredito(origin, startDate, endDate, state, sentimentoPredito);
        } else if (startDate != null && endDate != null && country != null && !country.isEmpty() && sentimentoPredito != null && !sentimentoPredito.isEmpty()) {
            return reviewRepository.findByOriginAndReviewCreationDateBetweenAndGeolocationCountryAndSentimentoPredito(origin, startDate, endDate, country, sentimentoPredito);
        } else if (startDate != null && endDate != null && state != null && !state.isEmpty()) {
            return reviewRepository.findByOriginAndReviewCreationDateBetweenAndGeolocationState(origin, startDate, endDate, state);
        } else if (startDate != null && endDate != null && country != null && !country.isEmpty()) {
            return reviewRepository.findByOriginAndReviewCreationDateBetweenAndGeolocationCountry(origin, startDate, endDate, country);
        } else if (startDate != null && endDate != null && sentimentoPredito != null && !sentimentoPredito.isEmpty()) {
            return reviewRepository.findByOriginAndReviewCreationDateBetweenAndSentimentoPredito(origin, startDate, endDate, sentimentoPredito);
        } else if (state != null && !state.isEmpty() && country != null && !country.isEmpty() && sentimentoPredito != null && !sentimentoPredito.isEmpty()) {
            return reviewRepository.findByOriginAndGeolocationStateAndGeolocationCountryAndSentimentoPredito(origin, state, country, sentimentoPredito);
        } else if (state != null && !state.isEmpty() && country != null && !country.isEmpty()) {
            return reviewRepository.findByOriginAndGeolocationStateAndGeolocationCountry(origin, state, country);
        } else if (state != null && !state.isEmpty() && sentimentoPredito != null && !sentimentoPredito.isEmpty()) {
            return reviewRepository.findByOriginAndGeolocationStateAndSentimentoPredito(origin, state, sentimentoPredito);
        } else if (country != null && !country.isEmpty() && sentimentoPredito != null && !sentimentoPredito.isEmpty()) {
            return reviewRepository.findByOriginAndGeolocationCountryAndSentimentoPredito(origin, country, sentimentoPredito);
        } else if (startDate != null && endDate != null) {
            return reviewRepository.findByOriginAndReviewCreationDateBetween(origin, startDate, endDate);
        } else if (state != null && !state.isEmpty()) {
            return reviewRepository.findByOriginAndGeolocationState(origin, state);
        } else if (country != null && !country.isEmpty()) {
            return reviewRepository.findByOriginAndGeolocationCountry(origin, country);
        } else if (sentimentoPredito != null && !sentimentoPredito.isEmpty()) {
            return reviewRepository.findByOriginAndSentimentoPredito(origin, sentimentoPredito);
        } else {
            return reviewRepository.findByOrigin(origin);
        }

    }

    public void generateCSV(String datasource, String startDateString, String endDateString, String state, String country, String sentimentoPredito) {
        List<Review> reviews = listReviewByFilters(datasource, startDateString, endDateString, state, country, sentimentoPredito);


        try (FileWriter writer = new FileWriter("review_report.csv")) {
            // Escrever a primeira linha personalizada em tamanho maior e centralizada
            writer.append("Report Review Sentiments\n");
            // Escrever a segunda linha com a data e hora de geração do relatório
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            String generatedDateTime = dateFormat.format(new Date());
            writer.append("Generation Date: ").append(generatedDateTime).append("\n");

            // Escrever as colunas com o cabeçalho verde
            writer.append("id,review_comment_message,review_score,predictions,geolocation_lat,geolocation_lng,geolocation_state,geolocation_country,geolocation_point,origin,review_creation_date,creationdate\n");
            for (Review review : reviews) {
                writer.append(review.getId()).append(",")
                        .append(review.getReviewCommentMessage()).append(",")
                        .append(review.getReviewScore()).append(",")
                        .append(review.getSentimentoPredito()).append(",")
                        .append(review.getGeolocationLat()).append(",")
                        .append(review.getGeolocationLng()).append(",")
                        .append(review.getGeolocationState()).append(",")
                        .append(review.getGeolocationCountry()).append(",")
                        .append(review.getGeolocation()).append(",")
                        .append(review.getOrigin()).append(",")
                        .append(review.getReviewCreationDate().toString()).append(",")
                        .append(review.getCreationdate().toString()).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Timestamp parseDateStringToTimestamp(String dateString) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date parsedDate = dateFormat.parse(dateString);
            return new Timestamp(parsedDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
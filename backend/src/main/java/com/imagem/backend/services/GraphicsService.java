package com.imagem.backend.services;


import com.imagem.backend.domain.Review;
import com.imagem.backend.repositories.ReviewRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
public class GraphicsService {

    private final ReviewRepository reviewRepository;

    public GraphicsService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }
    public List<Review> listReview(){
        log.info("Buscando os registros de review...");
        return reviewRepository.findAll();
    }
    
    public List<Review> listReviewByDateRange(Timestamp startDate, Timestamp endDate) {
        return reviewRepository.findByReviewCreationDateBetween(startDate, endDate);
    }

    public List<Review> listReviewByDateRangeState(Timestamp startDate, Timestamp endDate,String state) {
        return reviewRepository.findByGeolocationStateAndReviewCreationDateBetween(state,startDate, endDate);
    }

    public List<Review> listReviewByDateRangeAndSentiment(Timestamp startTimestamp, Timestamp endTimestamp, String sentimentoPredito) {
        return reviewRepository.findByReviewCreationDateBetweenAndSentimentoPredito(startTimestamp, endTimestamp, sentimentoPredito);
    }

    public List<Review> listReviewByDateRangeAndSentimentState(Timestamp startTimestamp, Timestamp endTimestamp, String sentimentoPredito,String state) {
        return reviewRepository.findByReviewCreationDateBetweenAndSentimentoPreditoAndGeolocationState(startTimestamp, endTimestamp, sentimentoPredito,state);
    }

}


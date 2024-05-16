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
import java.util.Optional;


@Service
@Slf4j
public class GraphicsService {

    private final ReviewRepository reviewRepository;

    public GraphicsService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public List<Review> listByDatasource(String origin){
        return this.reviewRepository.findByOrigin(origin);
    }

    public List<Review> listReview(){
        log.info("Buscando os registros de review...");
        return reviewRepository.findAll();
    }

    public List<Review> listReviewByDateRangeCountry(Timestamp startDate, Timestamp endDate, String country) {
        return reviewRepository.findByReviewCreationDateBetweenAndGeolocationCountry(startDate, endDate,country);
    }

    public List<Review> listReviewByDateRangeAndOrigin(Timestamp startDate, Timestamp endDate, String origin) {
        return reviewRepository.findByReviewCreationDateBetweenAndOrigin(startDate, endDate, origin);
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

    public List<Review> listReviewByDateRangeAndSentimentOrigin(Timestamp startTimestamp, Timestamp endTimestamp, String sentimentoPredito,String origin) {
        return reviewRepository.findByReviewCreationDateBetweenAndSentimentoPreditoAndOrigin(startTimestamp, endTimestamp, sentimentoPredito,origin);
    }

    public List<Review> listReviewByDateRangeAndCountryOrigin(Timestamp startTimestamp, Timestamp endTimestamp, String country, String origin) {
        return reviewRepository.findByReviewCreationDateBetweenAndGeolocationCountryAndOrigin(startTimestamp, endTimestamp,country,origin);
    }
    public List<Review> listReviewByDateRangeAndSentimentCountry(Timestamp startTimestamp, Timestamp endTimestamp, String sentimentoPredito, String country) {
        return reviewRepository.findByReviewCreationDateBetweenAndSentimentoPreditoAndGeolocationCountry(startTimestamp, endTimestamp, sentimentoPredito, country);
    }

    public List<Review> listReviewByDateRangeAndStateOrigin(Timestamp startTimestamp, Timestamp endTimestamp,String state, String origin) {
        return reviewRepository.findByReviewCreationDateBetweenAndGeolocationStateAndOrigin(startTimestamp, endTimestamp,state, origin);
    }

    public List<Review> listReviewByDateRangeAndSentimentState(Timestamp startTimestamp, Timestamp endTimestamp, String sentimentoPredito,String state) {
        return reviewRepository.findByReviewCreationDateBetweenAndSentimentoPreditoAndGeolocationState(startTimestamp, endTimestamp, sentimentoPredito,state);
    }

    public List<Review> listReviewByDateRangeAndStateCountry(Timestamp startTimestamp, Timestamp endTimestamp, String state, String country) {
        return reviewRepository.findByReviewCreationDateBetweenAndGeolocationCountryAndGeolocationState(startTimestamp, endTimestamp, country,state);
    }

    public List<Review> listReviewByDateRangeAndSentimentoCountryOrigin(Timestamp startTimestamp, Timestamp endTimestamp,String sentimento, String country, String origin) {
        return reviewRepository.findByReviewCreationDateBetweenAndSentimentoPreditoAndGeolocationCountryAndOrigin(startTimestamp, endTimestamp, sentimento, country, origin);
    }

    public List<Review> listReviewByDateRangeAndSentimentoStateOrigin(Timestamp startTimestamp, Timestamp endTimestamp,String sentimento, String state, String origin) {
        return reviewRepository.findByReviewCreationDateBetweenAndSentimentoPreditoAndGeolocationStateAndOrigin(startTimestamp, endTimestamp, sentimento, state, origin);
    }

    public List<Review> listReviewByDateRangeAndStateCountryOrigin(Timestamp startTimestamp, Timestamp endTimestamp, String state, String country, String origin) {
        return reviewRepository.findByReviewCreationDateBetweenAndGeolocationStateAndGeolocationCountryAndOrigin(startTimestamp, endTimestamp,state, country, origin);
    }

    public List<Review> listReviewByDateRangeAndSentimentStateCountry(Timestamp startTimestamp, Timestamp endTimestamp, String sentimentoPredito,String state, String country) {
        return reviewRepository.findByReviewCreationDateBetweenAndSentimentoPreditoAndGeolocationStateAndGeolocationCountry(startTimestamp, endTimestamp, sentimentoPredito,state, country);
    }

    public List<Review> listReviewByDateRangeAndSentimentStateCountryOrigin(Timestamp startTimestamp, Timestamp endTimestamp, String sentimentoPredito,String state, String country,String origin) {
        return reviewRepository.findByReviewCreationDateBetweenAndSentimentoPreditoAndGeolocationStateAndGeolocationCountryAndOrigin(startTimestamp, endTimestamp, sentimentoPredito,state, country,origin);
    }

    public List<String> listCountry(){
        return this.reviewRepository.findDistinctGeolocationCountry();
    }

    public List<String> listState(){
        return this.reviewRepository.findDistinctGeolocationState();
    }

    public List<String> listOrigin(){
        return this.reviewRepository.findDistinctOrigin();
    }

    public void deleteReview(Integer reviewId){
        Review review = this.reviewRepository.findById(reviewId).orElseThrow();

        this.reviewRepository.delete(review);
    }
}
package com.imagem.backend.services;


import com.imagem.backend.domain.Report;
import com.imagem.backend.domain.Review;
import com.imagem.backend.repositories.ReportRepository;
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

    private final ReportRepository reportRepository;

    public GraphicsService(ReviewRepository reviewRepository, ReportRepository reportRepository) {
        this.reviewRepository = reviewRepository;
        this.reportRepository = reportRepository;
    }

    public List<Review> listByDatasource(String origin){
        log.info("Buscando os datas sources");
        return this.reviewRepository.findByOrigin(origin);
    }

    public List<Review> listReview(){
        log.info("Buscando os registros de review...");
        return reviewRepository.findAll();
    }

    public List<Review> listReviewByDateRangeCountry(Timestamp startDate, Timestamp endDate, String country) {
        log.info("Realizando busca de review.");
        return reviewRepository.findByReviewCreationDateBetweenAndGeolocationCountry(startDate, endDate,country);
    }

    public List<Review> listReviewByDateRangeAndOrigin(Timestamp startDate, Timestamp endDate, String origin) {
        log.info("Realizando busca de review.");
        return reviewRepository.findByReviewCreationDateBetweenAndOrigin(startDate, endDate, origin);
    }
    
    public List<Review> listReviewByDateRange(Timestamp startDate, Timestamp endDate) {
        log.info("Realizando busca de review.");
        return reviewRepository.findByReviewCreationDateBetween(startDate, endDate);
    }

    public List<Review> listReviewByDateRangeState(Timestamp startDate, Timestamp endDate,String state) {
        log.info("Realizando busca de review.");
        return reviewRepository.findByGeolocationStateAndReviewCreationDateBetween(state,startDate, endDate);
    }

    public List<Review> listReviewByDateRangeAndSentiment(Timestamp startTimestamp, Timestamp endTimestamp, String sentimentoPredito) {
        log.info("Realizando busca de review.");
        return reviewRepository.findByReviewCreationDateBetweenAndSentimentoPredito(startTimestamp, endTimestamp, sentimentoPredito);
    }

    public List<Review> listReviewByDateRangeAndSentimentOrigin(Timestamp startTimestamp, Timestamp endTimestamp, String sentimentoPredito,String origin) {
        log.info("Realizando busca de review.");
        return reviewRepository.findByReviewCreationDateBetweenAndSentimentoPreditoAndOrigin(startTimestamp, endTimestamp, sentimentoPredito,origin);
    }

    public List<Review> listReviewByDateRangeAndCountryOrigin(Timestamp startTimestamp, Timestamp endTimestamp, String country, String origin) {
        log.info("Realizando busca de review.");
        return reviewRepository.findByReviewCreationDateBetweenAndGeolocationCountryAndOrigin(startTimestamp, endTimestamp,country,origin);
    }
    public List<Review> listReviewByDateRangeAndSentimentCountry(Timestamp startTimestamp, Timestamp endTimestamp, String sentimentoPredito, String country) {
        log.info("Realizando busca de review.");
        return reviewRepository.findByReviewCreationDateBetweenAndSentimentoPreditoAndGeolocationCountry(startTimestamp, endTimestamp, sentimentoPredito, country);
    }

    public List<Review> listReviewByDateRangeAndStateOrigin(Timestamp startTimestamp, Timestamp endTimestamp,String state, String origin) {
        log.info("Realizando busca de review.");
        return reviewRepository.findByReviewCreationDateBetweenAndGeolocationStateAndOrigin(startTimestamp, endTimestamp,state, origin);
    }

    public List<Review> listReviewByDateRangeAndSentimentState(Timestamp startTimestamp, Timestamp endTimestamp, String sentimentoPredito,String state) {
        log.info("Realizando busca de review.");
        return reviewRepository.findByReviewCreationDateBetweenAndSentimentoPreditoAndGeolocationState(startTimestamp, endTimestamp, sentimentoPredito,state);
    }

    public List<Review> listReviewByDateRangeAndStateCountry(Timestamp startTimestamp, Timestamp endTimestamp, String state, String country) {
        log.info("Realizando busca de review.");
        return reviewRepository.findByReviewCreationDateBetweenAndGeolocationCountryAndGeolocationState(startTimestamp, endTimestamp, country,state);
    }

    public List<Review> listReviewByDateRangeAndSentimentoCountryOrigin(Timestamp startTimestamp, Timestamp endTimestamp,String sentimento, String country, String origin) {
        log.info("Realizando busca de review.");
        return reviewRepository.findByReviewCreationDateBetweenAndSentimentoPreditoAndGeolocationCountryAndOrigin(startTimestamp, endTimestamp, sentimento, country, origin);
    }

    public List<Review> listReviewByDateRangeAndSentimentoStateOrigin(Timestamp startTimestamp, Timestamp endTimestamp,String sentimento, String state, String origin) {
        log.info("Realizando busca de review.");
        return reviewRepository.findByReviewCreationDateBetweenAndSentimentoPreditoAndGeolocationStateAndOrigin(startTimestamp, endTimestamp, sentimento, state, origin);
    }

    public List<Review> listReviewByDateRangeAndStateCountryOrigin(Timestamp startTimestamp, Timestamp endTimestamp, String state, String country, String origin) {
        log.info("Realizando busca de review.");
        return reviewRepository.findByReviewCreationDateBetweenAndGeolocationStateAndGeolocationCountryAndOrigin(startTimestamp, endTimestamp,state, country, origin);
    }

    public List<Review> listReviewByDateRangeAndSentimentStateCountry(Timestamp startTimestamp, Timestamp endTimestamp, String sentimentoPredito,String state, String country) {
        log.info("Realizando busca de review.");
        return reviewRepository.findByReviewCreationDateBetweenAndSentimentoPreditoAndGeolocationStateAndGeolocationCountry(startTimestamp, endTimestamp, sentimentoPredito,state, country);
    }

    public List<Review> listReviewByDateRangeAndSentimentStateCountryOrigin(Timestamp startTimestamp, Timestamp endTimestamp, String sentimentoPredito,String state, String country,String origin) {
        log.info("Realizando busca de review.");
        return reviewRepository.findByReviewCreationDateBetweenAndSentimentoPreditoAndGeolocationStateAndGeolocationCountryAndOrigin(startTimestamp, endTimestamp, sentimentoPredito,state, country,origin);
    }

    public List<String> listCountry(){
        log.info("Realizando busca de paises.");
        return this.reviewRepository.findDistinctGeolocationCountry();
    }

    public List<String> listState(){
        log.info("Realizando busca de estados.");
        return this.reviewRepository.findDistinctGeolocationState();
    }

    public List<String> listOrigin(){
        log.info("Realizando busca de origens.");
        return this.reviewRepository.findDistinctOrigin();
    }

    public void deleteReview(Integer reviewId){
        log.info("Realizando busca de review.");
        Review review = this.reviewRepository.findById(reviewId).orElseThrow();

        log.info("Realizando delete de review.");
        this.reviewRepository.delete(review);
    }

    public Review updateReview(Integer reviewId, String sentimentId) {

        log.info("Realizando busca de review.");
        Review review = this.reviewRepository.findById(reviewId).orElseThrow();
        log.info("Realizando troca de sentimento predito.");
        review.setSentimentoPredito(sentimentId);
        this.reviewRepository.save(review);

        return review;
    }
    public Report saveReport(Report report) {
        log.info("Realizando salvamento de report.");
        report.setData(new Timestamp(System.currentTimeMillis()));
        return reportRepository.save(report);
    }
}
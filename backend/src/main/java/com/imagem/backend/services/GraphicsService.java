package com.imagem.backend.services;


import com.imagem.backend.domain.Report;
import com.imagem.backend.domain.Review;
import com.imagem.backend.domain.User;
import com.imagem.backend.dtos.ClassifierDTO;
import com.imagem.backend.dtos.LogSender;
import com.imagem.backend.dtos.UserLog;
import com.imagem.backend.exceptions.ErrorSentiment;
import com.imagem.backend.exceptions.ErrorUpdateCsv;
import com.imagem.backend.exceptions.ReviewNotFound;
import com.imagem.backend.infra.ext.IntegrationAI;
import com.imagem.backend.infra.ext.LogProducerService;
import com.imagem.backend.infra.security.UserSession;
import com.imagem.backend.repositories.ReportRepository;
import com.imagem.backend.repositories.ReviewRepository;
import com.imagem.backend.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Service
@Slf4j
public class GraphicsService extends LogProducerService{

    private final ReviewRepository reviewRepository;

    private final ReportRepository reportRepository;

    private final UserSession userSession;

    private final IntegrationAI integrationAI;

    private final UserRepository userRepository;

    public GraphicsService(ReviewRepository reviewRepository, ReportRepository reportRepository, UserSession userSession, IntegrationAI integrationAI, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.reportRepository = reportRepository;
        this.userSession = userSession;
        this.integrationAI = integrationAI;
        this.userRepository = userRepository;
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

    public List<String> listState(String country){
        log.info("Realizando busca de estados.");
        return this.reviewRepository.findDistinctOriginByCountry(country);
    }

    public List<String> listOrigin(){
        log.info("Realizando busca de origens.");
        return this.reviewRepository.findDistinctOrigin();
    }

    public void deleteReview(Integer reviewId){
        log.info("Realizando busca de review.");
        Review review = this.reviewRepository.findById(reviewId).orElseThrow();

        log.info("Buscando o usuário logado...");
        User userLogged = userSession.userLogged();

        LogSender logObject = new LogSender();
        logObject.setUsuario(new UserLog(userLogged.getNome(), userLogged.getId()));
        logObject.setRegistro("The user deleted a review with the id equal to: " + review.getId());
        sendMessage(logObject);

        log.info("Realizando delete de review.");
        this.reviewRepository.delete(review);
    }

    public Review updateReview(Integer reviewId, String sentimentId) {

        log.info("Realizando busca de review.");
        Review review = this.reviewRepository.findById(reviewId).orElseThrow();
        log.info("Realizando troca de sentimento predito.");
        review.setSentimentoPredito(sentimentId);
        this.reviewRepository.save(review);

        log.info("Buscando o usuário logado...");
        User userLogged = userSession.userLogged();

        LogSender logObject = new LogSender();
        logObject.setUsuario(new UserLog(userLogged.getNome(), userLogged.getId()));
        logObject.setRegistro("The user updated a review with a new sentiment and the id equal to: " + review.getId());
        sendMessage(logObject);

        return review;
    }
    public Report saveReport(Report report) {
        log.info("Realizando salvamento de report.");
        report.setData(new Timestamp(System.currentTimeMillis()));

        log.info("Buscando o usuário logado...");
        User userLogged = (User) userRepository.findByUsername(report.getUserName());

        LogSender logObject = new LogSender();
        logObject.setUsuario(new UserLog(userLogged.getNome(), userLogged.getId()));
        logObject.setRegistro("The user generate a report from graphic " +report.getGraphicTitle() +" with id equal to: " + report.getId());
        sendMessage(logObject);

        return reportRepository.save(report);
    }

    public Review updateClassifier(Integer id, ClassifierDTO classifier){
        log.info("Realizando busca de review.");
        Review review = this.reviewRepository.findById(id).orElseThrow(ReviewNotFound::new);

        log.info("Buscando o usuário logado...");
        User userLogged = userSession.userLogged();

        LogSender logObject = new LogSender();
        logObject.setUsuario(new UserLog(userLogged.getNome(), userLogged.getId()));
        logObject.setRegistro("The user updated a review with a new classifiier and the id equal to: " + review.getId());
        sendMessage(logObject);


        log.info("Salvando a alteracao do review.");
        review.setClassifier(classifier.getClassifier());
        this.reviewRepository.save(review);
        return review;
    }

    public void uploadFile(MultipartFile file){

        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

            List<Review> reviews = new ArrayList<>();
            String datasource = null;
            br.readLine();
            String line;

            while (!(line = br.readLine()).isEmpty()) {

                String[] data = line.split(",");

                if(data.length < 1){
                    this.reviewRepository.saveAll(reviews);

                    log.info("Buscando o usuário logado...");
                    User user = userSession.userLogged();
                    LogSender logObject = new LogSender();
                    logObject.setUsuario(new UserLog(user.getNome(), user.getId()));
                    logObject.setRegistro("User uploaded a new datasource " + datasource);
                    sendMessage(logObject);
                    return;
                }

                Review review = new Review();
                review.setReviewCommentMessage(data[0]);
                review.setReviewScore(data[1]);
                System.out.println("sentiment " + data[0]);
                String sentimento = integrationAI.getSentiment(data[0]); // Ajuste aqui de acordo com a posição da coluna predictions
                review.setSentimentoPredito(sentimento);
                review.setGeolocationLat(data[3]);
                review.setGeolocationLng(data[4]);
                review.setGeolocationState(data[5]);
                review.setGeolocationCountry(data[6]);
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm");
                Date parsedDate = dateFormat.parse(data[7]);
                Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
                review.setReviewCreationDate(timestamp);

                review.setOrigin(data[8]);

                review.setGeolocation(data[9]);

                datasource = review.getOrigin();
                reviews.add(review);
            }

            User userLogged = userSession.userLogged();
            LogSender logObject = new LogSender();
            logObject.setUsuario(new UserLog(userLogged.getNome(), userLogged.getId()));
            logObject.setRegistro("User uploaded a datasource with the name: "+ datasource);
            sendMessage(logObject);


        } catch (IOException e) {
            throw new ErrorUpdateCsv();
        } catch (ParseException a) {
            throw new RuntimeException(a);
        }


    }
}
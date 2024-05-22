package com.imagem.backend.controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.imagem.backend.domain.Report;
import com.imagem.backend.domain.Review;
import com.imagem.backend.domain.Word;
import com.imagem.backend.dtos.ClassifierDTO;
import com.imagem.backend.dtos.GlobalResponseDTO;
import com.imagem.backend.services.GraphicsService;
import com.imagem.backend.services.ReportService;
import com.imagem.backend.services.WordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;


import java.util.Date;


@RestController
@RequestMapping("/graphics")
public class ReviewController {

    private final GraphicsService graphicsService;

    private final ReportService reportService;
    private final WordService wordService;

    public ReviewController(GraphicsService graphicsService, ReportService reportService, WordService wordService) {
        this.graphicsService = graphicsService;
        this.reportService = reportService;
        this.wordService = wordService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<Review>> listAllReview(){

        List<Review> reviewList = this.graphicsService.listReview();

        return ResponseEntity.ok().body(reviewList);
    }

    @GetMapping("/countries")
    public ResponseEntity<List<String>> listCountry(){

        List<String> countries = this.graphicsService.listCountry();
        return ResponseEntity.ok().body(countries);
    }

    @GetMapping("/states")
    public ResponseEntity<List<String>> listState(){

        List<String> states = this.graphicsService.listState();
        return ResponseEntity.ok().body(states);
    }

    @GetMapping("/datasource")
    public ResponseEntity<List<String>> listDatasources(){

        List<String> countries = this.graphicsService.listOrigin();
        return ResponseEntity.ok().body(countries);
    }

    @GetMapping("/listByDateRange")
    public ResponseEntity<List<Review>> listReviewByDateRange(
            @RequestParam("startDate") String startDateString,
            @RequestParam("endDate") String endDateString,
            @RequestParam(value = "state", required = false) String state,
            @RequestParam(value = "country", required = false) String country,
            @RequestParam(value = "datasource", required = false) String datasource,
            @RequestParam(value = "sentimentoPredito", required = false) String sentimentoPredito) {
        Timestamp startTimestamp = parseDateStringToTimestamp(startDateString);
        Timestamp endTimestamp = parseDateStringToTimestamp(endDateString);

        if (startTimestamp == null || endTimestamp == null) {
            return ResponseEntity.badRequest().build();
        }

        List<Review> reviewList;
        if (state != null && country != null && sentimentoPredito != null && datasource != null) {
            reviewList = this.graphicsService.listReviewByDateRangeAndSentimentStateCountryOrigin(startTimestamp, endTimestamp, sentimentoPredito, state, country, datasource); // Retorna 2 se todos os campos estiverem preenchidos
        } else if (state != null && country != null && sentimentoPredito != null) {
            reviewList = this.graphicsService.listReviewByDateRangeAndSentimentStateCountry(startTimestamp, endTimestamp, sentimentoPredito, state, country); // Retorna 3 se state, country e sentimentoPredito estiverem preenchidos
        } else if (state != null && country != null && datasource != null) {
            reviewList = this.graphicsService.listReviewByDateRangeAndStateCountryOrigin(startTimestamp, endTimestamp, state, country, datasource); // Retorna 4 se state, country e datasource estiverem preenchidos
        } else if (state != null && sentimentoPredito != null && datasource != null) {
            reviewList = this.graphicsService.listReviewByDateRangeAndSentimentoStateOrigin(startTimestamp, endTimestamp,sentimentoPredito, state, datasource); // Retorna 5 se state, sentimentoPredito e datasource estiverem preenchidos
        } else if (country != null && sentimentoPredito != null && datasource != null) {
            reviewList = this.graphicsService.listReviewByDateRangeAndSentimentoCountryOrigin(startTimestamp, endTimestamp,sentimentoPredito, country, datasource); // Retorna 6 se country, sentimentoPredito e datasource estiverem preenchidos
        } else if (state != null && country != null) {
            reviewList = this.graphicsService.listReviewByDateRangeAndStateCountry(startTimestamp, endTimestamp,state, country); // Retorna 7 se state e country estiverem preenchidos
        } else if (state != null && sentimentoPredito != null) {
            reviewList = this.graphicsService.listReviewByDateRangeAndSentimentState(startTimestamp, endTimestamp,sentimentoPredito, state); // Retorna 8 se state e sentimentoPredito estiverem preenchidos
        } else if (state != null && datasource != null) {
            reviewList = this.graphicsService.listReviewByDateRangeAndStateOrigin(startTimestamp, endTimestamp, state, datasource); // Retorna 9 se state e datasource estiverem preenchidos
        } else if (country != null && sentimentoPredito != null) {
            reviewList = this.graphicsService.listReviewByDateRangeAndSentimentCountry(startTimestamp, endTimestamp, sentimentoPredito, country); // Retorna 10 se country e sentimentoPredito estiverem preenchidos
        } else if (country != null && datasource != null) {
            reviewList = this.graphicsService.listReviewByDateRangeAndCountryOrigin(startTimestamp, endTimestamp,country, datasource); // Retorna 11 se country e datasource estiverem preenchidos
        } else if (sentimentoPredito != null && datasource != null) {
            reviewList = this.graphicsService.listReviewByDateRangeAndSentimentOrigin(startTimestamp, endTimestamp,sentimentoPredito, datasource); // Retorna 12 se sentimentoPredito e datasource estiverem preenchidos
        } else if (state != null) {
            reviewList = this.graphicsService.listReviewByDateRangeState(startTimestamp, endTimestamp, state); // Retorna 13 se apenas state estiver preenchido
        } else if (country != null) {
            reviewList = this.graphicsService.listReviewByDateRangeCountry(startTimestamp, endTimestamp,country); // Retorna 14 se apenas country estiver preenchido
        } else if (sentimentoPredito != null) {
            reviewList = this.graphicsService.listReviewByDateRangeAndSentiment(startTimestamp, endTimestamp, sentimentoPredito); // Retorna 15 se apenas sentimentoPredito estiver preenchido
        } else if (datasource != null) {
            reviewList = this.graphicsService.listReviewByDateRangeAndOrigin(startTimestamp, endTimestamp, datasource); // Retorna 16 se apenas datasource estiver preenchido
        } else {
            reviewList = this.graphicsService.listReviewByDateRange(startTimestamp, endTimestamp);// Retorna 17 se nenhum campo estiver preenchido
        }
        return ResponseEntity.ok().body(reviewList);
    }

    private Timestamp parseDateStringToTimestamp(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        try {
            Date date = dateFormat.parse(dateString);
            return new Timestamp(date.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/word")
    public ResponseEntity<List<Word>> listWords(){

        List<Word> words = this.wordService.listAllWords();

        return ResponseEntity.ok().body(words);
    }

    @GetMapping("/datasource/list/{origin}")
    public ResponseEntity<List<Review>> listByDatasource(
            @PathVariable(value = "origin") String datasource) {
        List<Review> listReview = this.graphicsService.listByDatasource(datasource);

        return ResponseEntity.ok().body(listReview);
    }

    @GetMapping("/review/report")
    public ResponseEntity<String> generateReport(
            @RequestParam(value = "startDate", required = false) String startDateString,
            @RequestParam(value = "endDate", required = false) String endDateString,
            @RequestParam(value = "state", required = false) String state,
            @RequestParam(value = "country", required = false) String country,
            @RequestParam(value = "origin", required = false) String origin,
            @RequestParam(value = "sentimentoPredito", required = false) String sentimentoPredito) {

        reportService.generateCSV(origin, startDateString, endDateString, state, country, sentimentoPredito);

        String csvFileName = "review_report.csv";

        return ResponseEntity.ok().body("Relatório gerado com sucesso! Arquivo CSV disponível em: " + csvFileName);
    }

    @DeleteMapping("/review/{id}")
    public ResponseEntity deleteReview(@PathVariable Integer id){
        this.graphicsService.deleteReview(id);
        return ResponseEntity.ok().body(new GlobalResponseDTO("Deletado com sucesso!"));
    }

    @PostMapping("/review/classifier/{id}")
    public ResponseEntity<Review> updateClassifier(@PathVariable("id") Integer id, @RequestBody ClassifierDTO classifier){
        System.out.println("asdasas");
        Review review = this.graphicsService.updateClassifier(id, classifier);

        return ResponseEntity.ok().body(review);
    }

    @PutMapping("/update/{revid}/{sentid}")
    public ResponseEntity<Review> updateReview(@PathVariable(value = "revid") Integer reviewId,
                                       @PathVariable(value = "sentid") String sentimentId) {
        Review review = this.graphicsService.updateReview(reviewId, sentimentId);
        return ResponseEntity.ok().body(review);
    }

    @PostMapping("/report/log")
    public ResponseEntity<Report> createReport(@RequestBody Report report) {
        Report savedGraphic = graphicsService.saveReport(report);
        return ResponseEntity.ok(savedGraphic);
    }
}
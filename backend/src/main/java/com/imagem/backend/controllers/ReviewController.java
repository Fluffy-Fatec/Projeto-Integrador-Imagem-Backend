package com.imagem.backend.controllers;

import com.imagem.backend.domain.Review;
import com.imagem.backend.domain.Word;
import com.imagem.backend.services.GraphicsService;
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

    private final WordService wordService;

    public ReviewController(GraphicsService graphicsService, WordService wordService) {
        this.graphicsService = graphicsService;
        this.wordService = wordService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<Review>> listAllReview(){

        List<Review> reviewList = this.graphicsService.listReview();

        return ResponseEntity.ok().body(reviewList);
    }

    @GetMapping("/listByDateRange")
    public ResponseEntity<List<Review>> listReviewByDateRange(
            @RequestParam("startDate") String startDateString,
            @RequestParam("endDate") String endDateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        try {
            Date startDate = dateFormat.parse(startDateString);
            Date endDate = dateFormat.parse(endDateString);

            Timestamp startTimestamp = new Timestamp(startDate.getTime());
            Timestamp endTimestamp = new Timestamp(endDate.getTime());

            List<Review> reviewList = graphicsService.listReviewByDateRange(startTimestamp, endTimestamp);
            return ResponseEntity.ok().body(reviewList);
        } catch (ParseException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/word")
    public ResponseEntity<List<Word>> listWords(){

        List<Word> words = this.wordService.listAllWords();

        return ResponseEntity.ok().body(words);
    }
}



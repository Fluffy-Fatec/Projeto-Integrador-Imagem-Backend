package com.imagem.backend.controllers;

import com.imagem.backend.domain.Review;
import com.imagem.backend.domain.Word;
import com.imagem.backend.dtos.DateDTO;
import com.imagem.backend.services.GraphicsService;
import com.imagem.backend.services.WordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


import org.springframework.web.bind.annotation.RequestBody;
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
    public ResponseEntity<List<Review>> listReviewByDateRange(@RequestBody DateDTO datedto) {
        List<Review> reviewList = graphicsService.listReviewByDateRange(datedto.getStartDate(), datedto.getEndDate());
        return ResponseEntity.ok().body(reviewList);
    }

    @GetMapping("/word")
    public ResponseEntity<List<Word>> listWords(){

        List<Word> words = this.wordService.listAllWords();

        return ResponseEntity.ok().body(words);
    }
}



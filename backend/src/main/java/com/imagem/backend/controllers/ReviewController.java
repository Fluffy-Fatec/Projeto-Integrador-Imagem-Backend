package com.imagem.backend.controllers;

import com.imagem.backend.domain.Review;
import com.imagem.backend.services.GraphicsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/graphics")
public class ReviewController {

    private final GraphicsService graphicsService;

    public ReviewController(GraphicsService graphicsService) {
        this.graphicsService = graphicsService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<Review>> listAllReview(){

        List<Review> reviewList = this.graphicsService.listReview();

        return ResponseEntity.ok().body(reviewList);
    }
}

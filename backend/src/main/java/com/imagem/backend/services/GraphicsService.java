package com.imagem.backend.services;


import com.imagem.backend.domain.Review;
import com.imagem.backend.repositories.ReviewRepository;
import lombok.extern.slf4j.Slf4j;
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
}


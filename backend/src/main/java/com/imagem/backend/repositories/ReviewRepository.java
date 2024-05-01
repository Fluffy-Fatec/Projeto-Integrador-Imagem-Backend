package com.imagem.backend.repositories;

import com.imagem.backend.domain.Review;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

	List<Review> findByReviewCreationDateBetween(Timestamp startDate, Timestamp endDate);

	List<Review> findByGeolocationStateAndReviewCreationDateBetween(String state,Timestamp startDate, Timestamp endDate);

	List<Review> findByReviewCreationDateBetweenAndSentimentoPredito(Timestamp startDate, Timestamp endDate,
																	 String sentimentoPredito);
	List<Review> findByReviewCreationDateBetweenAndSentimentoPreditoAndGeolocationState(Timestamp startDate,
																					Timestamp endDate,
																 					String sentimentoPredito,
																					String state);

}

package com.imagem.backend.repositories;

import com.imagem.backend.domain.Review;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

	List<Review> findByReviewCreationDateBetween(Timestamp startDate, Timestamp endDate);
	
}

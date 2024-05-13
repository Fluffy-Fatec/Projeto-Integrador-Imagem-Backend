package com.imagem.backend.repositories;

import com.imagem.backend.domain.Review;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

	List<Review> findByOrigin(String origin);

	List<Review> findByReviewCreationDateBetween(Timestamp startDate, Timestamp endDate);

	List<Review> findByReviewCreationDateBetweenAndOrigin(Timestamp startDate, Timestamp endDate, String origin);


	List<Review> findByReviewCreationDateBetweenAndGeolocationCountry(Timestamp startDate, Timestamp endDate, String country);

	List<Review> findByGeolocationStateAndReviewCreationDateBetween(String state,Timestamp startDate, Timestamp endDate);

	List<Review> findByReviewCreationDateBetweenAndGeolocationCountryAndOrigin(Timestamp startDate, Timestamp endDate,String country, String origin);
	List<Review> findByReviewCreationDateBetweenAndSentimentoPreditoAndGeolocationCountry(Timestamp startDate, Timestamp endDate,
																	 String sentimentoPredito,String country);
	List<Review> findByReviewCreationDateBetweenAndSentimentoPredito(Timestamp startDate, Timestamp endDate,
																	 String sentimentoPredito);
	List<Review> findByReviewCreationDateBetweenAndGeolocationStateAndOrigin(Timestamp startDate,
																						Timestamp endDate,
																						String state,
																						String origin);
	List<Review> findByReviewCreationDateBetweenAndSentimentoPreditoAndOrigin(Timestamp startDate,
																						Timestamp endDate,
																						String sentimentoPredito,
																						String origin);
	List<Review> findByReviewCreationDateBetweenAndSentimentoPreditoAndGeolocationState(Timestamp startDate,
																					Timestamp endDate,
																 					String sentimentoPredito,
																					String state);
	List<Review> findByReviewCreationDateBetweenAndSentimentoPreditoAndGeolocationStateAndGeolocationCountry(
																					Timestamp startDate,
																					Timestamp endDate,
																 					String sentimentoPredito,
																					String state,
																					String country);
	List<Review> findByReviewCreationDateBetweenAndSentimentoPreditoAndGeolocationStateAndGeolocationCountryAndOrigin(
			Timestamp startDate,
			Timestamp endDate,
			String sentimentoPredito,
			String state,
			String country,
			String origin);

	List<Review> findByReviewCreationDateBetweenAndGeolocationStateAndGeolocationCountryAndOrigin(
			Timestamp startDate,
			Timestamp endDate,
			String state,
			String country,
			String origin);

	List<Review> findByReviewCreationDateBetweenAndGeolocationCountryAndGeolocationState(
			Timestamp startDate,
			Timestamp endDate,
			String country,
			String state);

	List<Review> findByReviewCreationDateBetweenAndSentimentoPreditoAndGeolocationCountryAndOrigin(
			Timestamp startDate,
			Timestamp endDate,
			String sentimentoPredito,
			String country,
			String origin);
	List<Review> findByReviewCreationDateBetweenAndSentimentoPreditoAndGeolocationStateAndOrigin(
			Timestamp startDate,
			Timestamp endDate,
			String sentimentoPredito,
			String state,
			String origin);

	@Query("SELECT DISTINCT r.geolocationCountry FROM Review r")
	List<String> findDistinctGeolocationCountry();

	@Query("SELECT DISTINCT r.geolocationState FROM Review r")
	List<String> findDistinctGeolocationState();
	
	@Query("SELECT DISTINCT r.origin FROM Review r")
	List<String> findDistinctOrigin();

	List<Review> findByOriginAndReviewCreationDateBetween(String origin, Timestamp startDate, Timestamp endDate);

	List<Review> findByOriginAndGeolocationState(String origin, String state);

	List<Review> findByOriginAndGeolocationCountry(String origin, String country);

	List<Review> findByOriginAndSentimentoPredito(String origin, String sentimentoPredito);

	List<Review> findByOriginAndReviewCreationDateBetweenAndGeolocationState(String origin, Timestamp startDate, Timestamp endDate, String state);

	List<Review> findByOriginAndReviewCreationDateBetweenAndGeolocationCountry(String origin, Timestamp startDate, Timestamp endDate, String country);

	List<Review> findByOriginAndReviewCreationDateBetweenAndSentimentoPredito(String origin, Timestamp startDate, Timestamp endDate, String sentimentoPredito);

	List<Review> findByOriginAndGeolocationStateAndGeolocationCountry(String origin, String state, String country);

	List<Review> findByOriginAndGeolocationStateAndSentimentoPredito(String origin, String state, String sentimentoPredito);

	List<Review> findByOriginAndGeolocationCountryAndSentimentoPredito(String origin, String country, String sentimentoPredito);

	List<Review> findByOriginAndReviewCreationDateBetweenAndGeolocationStateAndGeolocationCountry(String origin, Timestamp startDate, Timestamp endDate, String state, String country);

	List<Review> findByOriginAndReviewCreationDateBetweenAndGeolocationStateAndSentimentoPredito(String origin, Timestamp startDate, Timestamp endDate, String state, String sentimentoPredito);

	List<Review> findByOriginAndReviewCreationDateBetweenAndGeolocationCountryAndSentimentoPredito(String origin, Timestamp startDate, Timestamp endDate, String country, String sentimentoPredito);

	List<Review> findByOriginAndGeolocationStateAndGeolocationCountryAndSentimentoPredito(String origin, String state, String country, String sentimentoPredito);

	List<Review> findByOriginAndReviewCreationDateBetweenAndGeolocationStateAndGeolocationCountryAndSentimentoPredito(String origin, Timestamp startDate, Timestamp endDate, String state, String country, String sentimentoPredito);

}

package com.imagem.backend.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "review")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private String id;

    @Column(name = "review_comment_message")
    private String reviewCommentMessage;

    @Column(name = "review_score")
    private String reviewScore;

    @Column(name = "sentimento_predito")
    private String sentimentoPredito;

    @Column(name = "geolocation_lat")
    private String geolocationLat;

    @Column(name = "geolocation_lng")
    private String geolocationLng;

    @Column(name = "geolocation_state")
    private String geolocationState;

    @Column(name = "geolocation_point")
    private String geolocation;

    @Column(name = "review_creation_date")
    private Timestamp reviewCreationDate;

    @Column(name = "creationdate", nullable = false, insertable = false, updatable = false)
    private Timestamp creationdate;
}

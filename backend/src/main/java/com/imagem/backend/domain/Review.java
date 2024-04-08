package com.imagem.backend.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//Tipos do point
//import org.springframework.data.geo.Point;
//import java.awt.*;
import net.postgis.jdbc.geometry.Point;

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

    @Column(name = "review_score")
    private String reviewScore;

    @Column(name = "review_comment_title")
    private String reviewCommentTitle;

    @Column(name = "review_comment_message")
    private String reviewCommentMessage;

    @Column(name = "review_creation_date")
    private Timestamp reviewCreationDate;

    @Column(name = "review_answer_timestamp")
    private Timestamp reviewAnswerTimestamp;

    @Column(name = "product_id")
    private String productId;

    @Column(name = "geolocation")
    private Point geolocation;

    @Column(name = "sentiment")
    private String sentiment;

    @Column(name = "creationdate", nullable = false, insertable = false, updatable = false)
    private Timestamp creationdate;
}

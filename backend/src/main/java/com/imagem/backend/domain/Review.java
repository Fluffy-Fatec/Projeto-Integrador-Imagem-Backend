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
    private Long id;

    @Column(length = 255, nullable = false)
    private String review;

    @Column(length = 255, nullable = true)
    private String comentario;

    @Column(length = 255, nullable = true)
    private String sentimento;

    @Column(name = "titulo", length = 255, nullable = false)
    private String title;

    @Column(length = 255, nullable = true)
    private String estado;

    @Column(length = 255, nullable = true)
    private String cidade;

    @Column(length = 255, nullable = true)
    private String rua;

    @Column(nullable = true)
    private Double lat;

    @Column(nullable = true)
    private Double lon;

    @Column(name = "creationDate", nullable = false)
    private Timestamp creationDate;
}

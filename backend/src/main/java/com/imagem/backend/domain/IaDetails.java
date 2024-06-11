package com.imagem.backend.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "ia_details")
public class IaDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

    @Column(name = "ia_score")
    private BigDecimal iaScore;

    @Column(name = "id_quantity_sentiment")
    private Integer idQuantitySentiment;

    @Column(name = "ia_datatime_deploy")
    private LocalDateTime iaDatetimeDeploy;
}

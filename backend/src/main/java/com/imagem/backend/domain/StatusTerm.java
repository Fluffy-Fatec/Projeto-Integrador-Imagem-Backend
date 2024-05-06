package com.imagem.backend.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="status_termo")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusTerm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "idtermo")
    private Term termo;

    @ManyToOne
    @JoinColumn(name = "iduser")
    private User user;

    private String status;

    private LocalDateTime dataaprovacao;

    private LocalDateTime creationdate;

}

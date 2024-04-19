package com.imagem.backend.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Table(name="Termo")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Term {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String termo;
    private String versao;
    private boolean atualVersao;
    private LocalDateTime creationDate;

}


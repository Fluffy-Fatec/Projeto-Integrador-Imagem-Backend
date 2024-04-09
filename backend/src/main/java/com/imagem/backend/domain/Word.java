package com.imagem.backend.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "palavra")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Word {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private String id;

    @Column(name = "word")
    private String word;

    @Column(name = "count")
    private String count;

    @Column(name = "sentiment")
    private String sentiment;

}

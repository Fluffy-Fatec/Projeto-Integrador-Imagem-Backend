package com.imagem.backend.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "notificacao_termo")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationTerm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idtermo", nullable = true)
    private Term term;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "iduser")
    private User user;

    @Column(length = 255, nullable = true)
    private String flagNotificacao;

    @Column(length = 255, nullable = true)
    private String mensagem;

    @Column(nullable = false, insertable = false)
    private Timestamp creationdate;
}

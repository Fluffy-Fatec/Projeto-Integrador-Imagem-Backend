package com.imagem.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "notificacao")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_alteracao_campo", nullable = true)
    private FieldChange fieldChange;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idadmin", nullable = true)
    private User admin;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "iduser")
    private User user;

    @Column(length = 255, nullable = true)
    private String tipoNotificacao;

    @Column(length = 255, nullable = true)
    private String flagNotificacao;

    @Column(length = 255, nullable = true)
    private String mensagem;

    @Column(nullable = false, insertable = false)
    private Timestamp creationdate;
}

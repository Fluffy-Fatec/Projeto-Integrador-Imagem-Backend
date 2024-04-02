package com.imagem.backend.domain;

import com.imagem.backend.domain.ENUM.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "alteracao_campo")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FieldChange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idAdmin")
    private User admin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUser")
    private User user;

    @Column(length = 255, nullable = true)
    private String novoUsername;

    @Column(length = 255, nullable = true)
    private String novoNome;

    @Column(length = 255, nullable = true)
    private String novoEmail;

    @Column(length = 255, nullable = true)
    private String novoCelular;

    @Column(length = 255, nullable = true)
    private String novoCpf;

    @Column(length = 255, nullable = true)
    private String status;

    @Column(name = "dataAprovacao", nullable = false)
    private Timestamp dataAprovacao;

    @Column(name = "dataRejeicao", nullable = false)
    private Timestamp dataRejeicao;

    @Column(name = "creationDate", nullable = false, insertable = false)
    private Timestamp creationDate;
}

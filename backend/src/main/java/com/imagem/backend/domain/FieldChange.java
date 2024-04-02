package com.imagem.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idadmin", nullable = true)
    private User admin;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "iduser")
    private User user;

    @Column(length = 255, nullable = true)
    private String novousername;

    @Column(length = 255, nullable = true)
    private String novonome;

    @Column(length = 255, nullable = true)
    private String novoemail;

    @Column(length = 255, nullable = true)
    private String novocelular;

    @Column(length = 255, nullable = true)
    private String novocpf;

    @Column(length = 255, nullable = true)
    private String status;

    @Column(name = "dataaprovacao", nullable = false)
    private Timestamp dataAprovacao;

    @Column(name = "datarejeicao", nullable = false)
    private Timestamp dataRejeicao;

    @Column(nullable = false, insertable = false)
    private Timestamp creationdate;
}

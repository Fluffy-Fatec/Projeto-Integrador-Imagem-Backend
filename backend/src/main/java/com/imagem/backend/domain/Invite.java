package com.imagem.backend.domain;

import com.imagem.backend.domain.ENUM.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "invite")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Invite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 255, nullable = true)
    private String email;

    @Column(length = 255, nullable = true)
    private String tokeninvite;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "solicitante")
    private User solicitante;

    @Column(nullable = true, insertable = false)
    private Timestamp creationdate;
}

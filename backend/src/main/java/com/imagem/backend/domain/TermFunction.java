package com.imagem.backend.domain;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@Table(name="termo_funcao")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TermFunction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="func_name")
    private String funcName;

    private LocalDateTime creationdate;

}
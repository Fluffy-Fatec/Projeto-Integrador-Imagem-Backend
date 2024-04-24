package com.imagem.backend.dtos;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class DataDTO {
    private Timestamp startDate;
    private Timestamp endDate;
}
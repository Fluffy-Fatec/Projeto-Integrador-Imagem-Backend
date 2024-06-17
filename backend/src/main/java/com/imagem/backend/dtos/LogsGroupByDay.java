package com.imagem.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogsGroupByDay {

    private String dateTime;

    private Integer countNewUsers;
}


package com.imagem.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class UserLog {

    private String name;

    private Integer id;
}

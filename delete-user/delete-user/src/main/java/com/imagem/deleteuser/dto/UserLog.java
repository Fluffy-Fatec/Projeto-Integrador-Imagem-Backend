package com.imagem.deleteuser.dto;

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

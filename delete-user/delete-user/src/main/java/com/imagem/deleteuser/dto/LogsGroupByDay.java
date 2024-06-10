package com.imagem.deleteuser.dto;

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

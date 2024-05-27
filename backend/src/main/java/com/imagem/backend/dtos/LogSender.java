package com.imagem.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogSender{

    private String creationDate = LocalDateTime.now().toString();

    private String registro;

    private UserLog usuario;

}

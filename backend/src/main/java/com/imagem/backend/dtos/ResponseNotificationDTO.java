package com.imagem.backend.dtos;

import lombok.Data;

@Data
public class ResponseNotificationDTO{

    private Integer id;

    private String tipo_notificacao;

    private String flag_notificacao;

    private String mensagem;

}

package com.imagem.backend.domain.ENUM;

public enum FlagNotificacaoEnum {

    VIST("1"),
    NO_VIST("2");

    private final String status;
    FlagNotificacaoEnum(String status){
        this.status = status;
    }

    public String getStatus(){
        return status;
    }
}

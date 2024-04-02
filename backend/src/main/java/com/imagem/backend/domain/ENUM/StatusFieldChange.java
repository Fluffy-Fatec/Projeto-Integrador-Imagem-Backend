package com.imagem.backend.domain.ENUM;

public enum StatusFieldChange {

    PENDENTE("pendente"),
    REJEITADO("rejeitado"),
    APROVADO("aprovado");

    private final String status;
    StatusFieldChange(String status){
        this.status = status;
    }

    public String getStatus(){
        return status;
    }
}

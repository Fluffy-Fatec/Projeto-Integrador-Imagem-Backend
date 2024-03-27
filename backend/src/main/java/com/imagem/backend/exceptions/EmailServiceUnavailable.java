package com.imagem.backend.exceptions;

public class EmailServiceUnavailable extends RuntimeException{

    public EmailServiceUnavailable(){
        super("O serviço de envio de email está indisponível no momento");
    }

    public EmailServiceUnavailable(String message){
        super(message);
    }
}


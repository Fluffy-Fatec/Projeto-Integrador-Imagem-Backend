package com.imagem.backend.exceptions;

public class EmailAlreadyInvited extends RuntimeException{

    public EmailAlreadyInvited(){
        super("Este email ja foi convidado a entrar na organização.");
    }

    public EmailAlreadyInvited(String message){
        super(message);
    }
}

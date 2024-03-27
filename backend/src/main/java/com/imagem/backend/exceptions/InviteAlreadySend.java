package com.imagem.backend.exceptions;

public class InviteAlreadySend extends RuntimeException{

    public InviteAlreadySend(){
        super("O envio do convite ja foi realizado a este email");
    }

    public InviteAlreadySend(String message){
        super(message);
    }
}

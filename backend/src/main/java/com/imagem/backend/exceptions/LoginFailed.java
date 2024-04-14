package com.imagem.backend.exceptions;

public class LoginFailed extends RuntimeException{

    public LoginFailed(){
        super("Falha ao realizar login.");
    }

    public LoginFailed(String message){
        super(message);
    }
}

package com.imagem.backend.exceptions;

public class InvalidPassword extends RuntimeException{

    public InvalidPassword(){
        super("A senha é invalida");
    }

    public InvalidPassword(String message){
        super(message);
    }
}

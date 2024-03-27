package com.imagem.backend.exceptions;

public class InvalidCelphone extends RuntimeException{

    public InvalidCelphone(){
        super("O celular é invalido");
    }

    public InvalidCelphone(String message){
        super(message);
    }
}

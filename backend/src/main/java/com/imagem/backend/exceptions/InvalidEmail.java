package com.imagem.backend.exceptions;

public class InvalidEmail extends RuntimeException{

    public InvalidEmail(){
        super("O email é inválido");
    }

    public InvalidEmail(String message){
        super(message);
    }
}

package com.imagem.backend.exceptions;

public class UserCreateRequestNotValidException extends RuntimeException{

    public UserCreateRequestNotValidException(){
        super("Não pode haver campos nulos, vazios ou em branco");
    }
    public UserCreateRequestNotValidException(String message){
        super(message);
    }
}

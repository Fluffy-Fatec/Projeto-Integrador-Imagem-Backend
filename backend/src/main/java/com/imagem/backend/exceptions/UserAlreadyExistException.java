package com.imagem.backend.exceptions;

public class UserAlreadyExistException extends RuntimeException{

    public UserAlreadyExistException(){
        super("Usuário ja existente");
    }

    public UserAlreadyExistException(String message){
        super(message);
    }
}

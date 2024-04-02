package com.imagem.backend.exceptions;

public class UserNotExist extends RuntimeException{

    public UserNotExist(){
        super("Usuário não esta autenticado");
    }
    public UserNotExist(String message){
        super(message);
    }
}

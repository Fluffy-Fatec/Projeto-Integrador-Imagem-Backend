package com.imagem.backend.exceptions;

public class UserNotAuthenticated extends RuntimeException{

    public UserNotAuthenticated(){
        super("Usuário não esta autenticado");
    }
    public UserNotAuthenticated(String message){
        super(message);
    }
}

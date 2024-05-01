package com.imagem.backend.exceptions;

public class FirstTimeTermAccepted extends RuntimeException{

    public FirstTimeTermAccepted(){
        super("First time term");
    }

    public FirstTimeTermAccepted(String message){
        super(message);
    }

}

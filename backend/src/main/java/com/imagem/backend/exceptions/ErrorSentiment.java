package com.imagem.backend.exceptions;

public class ErrorSentiment extends RuntimeException{

    public ErrorSentiment(){
        super("Error to get sentiment");
    }

    public ErrorSentiment(String message){
        super(message);
    }
}

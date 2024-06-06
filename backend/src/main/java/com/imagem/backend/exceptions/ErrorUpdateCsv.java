package com.imagem.backend.exceptions;

public class ErrorUpdateCsv extends RuntimeException{

    public ErrorUpdateCsv(){
        super("Error to upload CSV");
    }

    public ErrorUpdateCsv(String message){
        super(message);
    }
}

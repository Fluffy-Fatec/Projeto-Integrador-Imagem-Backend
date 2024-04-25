package com.imagem.backend.exceptions;

public class TermNotAccepted extends RuntimeException{

    public TermNotAccepted(){
        super("No Accepted");
    }

    public TermNotAccepted(String message){
        super(message);
    }
}
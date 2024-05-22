package com.imagem.backend.exceptions;

public class ReviewNotFound extends RuntimeException{

    public ReviewNotFound(){
        super("Review não encontrado.");
    }

    public ReviewNotFound(String message){
        super(message);
    }
}

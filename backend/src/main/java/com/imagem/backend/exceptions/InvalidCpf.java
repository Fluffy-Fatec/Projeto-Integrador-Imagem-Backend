package com.imagem.backend.exceptions;

public class InvalidCpf extends RuntimeException{

    public InvalidCpf(){
        super("O cpf é invalido");
    }

    public InvalidCpf(String message){
        super(message);
    }
}

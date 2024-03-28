package com.imagem.backend.dtos;


public record GlobalResponseDTO(String message) {
    public GlobalResponseDTO(String message) {
        this.message = message;
    }
}

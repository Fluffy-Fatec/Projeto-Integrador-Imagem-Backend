package com.imagem.backend.dtos;


public record RegisterResponseDTO(String message) {
    public RegisterResponseDTO(String message) {
        this.message = message;
    }
}

package com.imagem.backend.dtos;


public record UserFieldChangeResponseDTO(Integer id, String username, String nome,
                                         String email, String celular, String cpf) {
    public UserFieldChangeResponseDTO(Integer id, String username, String nome, String email, String celular, String cpf) {
        this.id = id;
        this.username = username;
        this.nome = nome;
        this.email = email;
        this.celular = celular;
        this.cpf = cpf;
    }

}

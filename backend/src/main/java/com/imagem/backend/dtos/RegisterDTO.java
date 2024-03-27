package com.imagem.backend.dtos;

import com.imagem.backend.domain.ENUM.UserRole;

public record RegisterDTO(String username, String password, String nome,  String email, String celular, String cpf) {
}

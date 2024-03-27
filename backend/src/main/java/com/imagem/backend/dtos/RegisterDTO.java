package com.imagem.backend.dtos;

import com.imagem.backend.domain.UserRole;

public record RegisterDTO(String username, String password, UserRole role) {
}

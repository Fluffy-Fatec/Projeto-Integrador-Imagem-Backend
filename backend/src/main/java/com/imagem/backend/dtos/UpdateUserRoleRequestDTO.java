package com.imagem.backend.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UpdateUserRoleRequestDTO(@JsonProperty("role") String role, @JsonProperty("id") Integer id) {
}

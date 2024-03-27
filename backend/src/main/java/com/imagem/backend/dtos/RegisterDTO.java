package com.imagem.backend.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RegisterDTO(@NotNull @JsonProperty("username")String username, @NotNull @JsonProperty("password") String password,
                          @NotNull @JsonProperty("name") String nome, @JsonProperty("celphone") String celular,
                          @NotNull @JsonProperty("cpf") String cpf) {
}

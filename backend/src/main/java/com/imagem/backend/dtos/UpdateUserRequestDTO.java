package com.imagem.backend.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public record UpdateUserRequestDTO(@NotNull @NotEmpty @NotBlank @JsonProperty("username")String username,
                          @NotNull @NotEmpty @NotBlank @JsonProperty("email") String email,
                          @NotNull @NotEmpty @NotBlank @JsonProperty("name") String nome,
                          @NotEmpty @NotEmpty @NotBlank @JsonProperty("celphone") String celular,
                          @NotNull @NotEmpty @NotBlank @JsonProperty("cpf") String cpf) {
}

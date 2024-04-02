package com.imagem.backend.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.imagem.backend.domain.ENUM.StatusFieldChange;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public record UserUpdateApproveRequestDTO(@NotNull @NotEmpty @NotBlank @JsonProperty("approve") String approve,
                                          @JsonProperty("id") Integer id) {
}

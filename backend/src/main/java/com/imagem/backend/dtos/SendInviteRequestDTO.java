package com.imagem.backend.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SendInviteRequestDTO(@JsonProperty("email_invited") String emailInvited) {
}

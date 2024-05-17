package com.imagem.backend.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record AcceptFucntionsRequest(@JsonProperty("functionId") List<Integer> functionId,
                                     @JsonProperty("username") String username,
                                     @JsonProperty("termAccepted") String statusTerm) {
}

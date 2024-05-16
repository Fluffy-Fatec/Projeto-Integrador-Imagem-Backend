package com.imagem.backend.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record AcceptFucntionsRequest(@JsonProperty("function_id") List<Integer> functionId,
                                     @JsonProperty("username") String username) {
}

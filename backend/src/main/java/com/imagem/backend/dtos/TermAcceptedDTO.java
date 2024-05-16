package com.imagem.backend.dtos;

import java.util.List;

public record TermAcceptedDTO (String username, String termAccepted, List<Integer> functionsId){
}

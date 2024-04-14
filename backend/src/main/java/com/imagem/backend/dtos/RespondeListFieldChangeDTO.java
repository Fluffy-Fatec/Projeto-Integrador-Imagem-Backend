package com.imagem.backend.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Timestamp;


public record RespondeListFieldChangeDTO(@JsonProperty("id") Integer id, @JsonProperty("admin") UserFieldChangeResponseDTO admin,
                                         @JsonProperty("user") UserFieldChangeResponseDTO user, @JsonProperty("novo_username") String novousername,
                                         @JsonProperty("novo_nome") String novonome, @JsonProperty("novo_email")String novoemail,
                                         @JsonProperty("novo_celular") String novocelular, @JsonProperty("novo_cpf") String novocpf,
                                         @JsonProperty("status") String status, @JsonProperty("data_aprovacao") Timestamp dataAprovacao,
                                         @JsonProperty("data_rejeicao") Timestamp dataRejeicao) {


}

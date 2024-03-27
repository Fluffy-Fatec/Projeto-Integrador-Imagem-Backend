package com.imagem.backend.validators;

import com.imagem.backend.dtos.RegisterDTO;
import com.imagem.backend.exceptions.UserCreateRequestNotValidException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceValidator {


    public void apply(RegisterDTO dto){
        this.userCreateValidator(dto);
    }

    private void userCreateValidator(RegisterDTO dto){
        if(dto.username() == null || dto.username().isBlank() || dto.username().isEmpty()
                || dto.password() == null || dto.password().isBlank() || dto.password().isEmpty()
                || dto.celular() == null || dto.celular().isBlank() || dto.celular().isEmpty()
                || dto.nome() == null || dto.nome().isBlank() || dto.nome().isEmpty()
                || dto.email() == null || dto.email().isBlank() || dto.email().isEmpty()
                || dto.cpf() == null || dto.cpf().isBlank() || dto.cpf().isEmpty()){
            throw new UserCreateRequestNotValidException();
        }

    }
}

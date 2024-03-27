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
                || dto.password() == null || dto.password().isBlank() || dto.password().isEmpty()){
            throw new UserCreateRequestNotValidException();
        }

    }
}

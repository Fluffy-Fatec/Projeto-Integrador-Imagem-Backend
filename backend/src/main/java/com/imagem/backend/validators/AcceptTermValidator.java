package com.imagem.backend.validators;

import com.imagem.backend.domain.StatusTerm;
import com.imagem.backend.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AcceptTermValidator {

    public void acceptTermTrue(User user){
        List<StatusTerm> listStatusTerm = user.getStatusTerm();

        for(StatusTerm statusTerm: listStatusTerm){
            statusTerm.get
        }

    }
}

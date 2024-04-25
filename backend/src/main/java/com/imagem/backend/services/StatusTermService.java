package com.imagem.backend.services;

import com.imagem.backend.domain.StatusTerm;
import com.imagem.backend.domain.Term;
import com.imagem.backend.domain.User;
import com.imagem.backend.dtos.TermAcceptedDTO;
import com.imagem.backend.exceptions.TermNotAccepted;
import com.imagem.backend.repositories.StatusTermoRepository;
import com.imagem.backend.repositories.TermRepository;
import com.imagem.backend.repositories.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class StatusTermService {

    private final StatusTermoRepository statusTermoRepository;

    private final TermRepository termRepository;

    private final UserRepository userRepository;


    public StatusTermService(StatusTermoRepository statusTermoRepository, TermRepository termRepository,
                             UserRepository userRepository) {
        this.statusTermoRepository = statusTermoRepository;
        this.termRepository = termRepository;
        this.userRepository = userRepository;
    }


    public void verifyTermAccepted(String username){
        log.info("Buscando pela atual versao do termo");
        Term termo = this.termRepository.findByAtualVersao(true);
        log.info("Buscando pelo usuario");
        User user = (User) this.userRepository.findByUsername(username);
        log.info("Buscando pelo termo e pelo usuario");
        StatusTerm statusTerm = this.statusTermoRepository.findByTermoAndUser(termo,user);

        log.info("Validacao do termo de aceite");
        if(statusTerm == null || statusTerm.getStatus().equals("rejected")) {

            throw new TermNotAccepted();
        }

    }


    public void termAccept(TermAcceptedDTO termAcceptedDTO){
        log.info("Buscando pela atual versao do termo");
        Term termo = this.termRepository.findByAtualVersao(true);
        log.info("Buscando pelo usuario");
        User user = (User) this.userRepository.findByUsername(termAcceptedDTO.username());
        log.info("Buscando pelo termo e pelo usuario");
        StatusTerm statusTerm = this.statusTermoRepository.findByTermoAndUser(termo,user);

        log.info("Associando o termo e a reposta do usuario");
        if(statusTerm ==  null){
            StatusTerm newStatusTerm = new StatusTerm();
            newStatusTerm.setUser(user);
            newStatusTerm.setTermo(termo);
            newStatusTerm.setStatus(termAcceptedDTO.termAccepted());
            this.statusTermoRepository.save(newStatusTerm);
        }else{
            statusTerm.setStatus(termAcceptedDTO.termAccepted());
            this.statusTermoRepository.save(statusTerm);
        }
    }
}
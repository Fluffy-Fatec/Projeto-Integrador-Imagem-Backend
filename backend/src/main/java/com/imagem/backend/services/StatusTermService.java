package com.imagem.backend.services;

import com.imagem.backend.domain.*;
import com.imagem.backend.dtos.TermAcceptedDTO;
import com.imagem.backend.exceptions.FirstTimeTermAccepted;
import com.imagem.backend.exceptions.TermNotAccepted;
import com.imagem.backend.infra.security.UserSession;
import com.imagem.backend.repositories.NotificationTermRepository;
import com.imagem.backend.repositories.StatusTermoRepository;
import com.imagem.backend.repositories.TermRepository;
import com.imagem.backend.repositories.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class StatusTermService {

    private final StatusTermoRepository statusTermoRepository;

    private final TermRepository termRepository;

    private final UserRepository userRepository;

    private final NotificationTermRepository notificationTermRepository;

    private final UserSession userSession;


    public StatusTermService(StatusTermoRepository statusTermoRepository, TermRepository termRepository,
                             UserRepository userRepository, NotificationTermRepository notificationTermRepository, UserSession userSession) {
        this.statusTermoRepository = statusTermoRepository;
        this.termRepository = termRepository;
        this.userRepository = userRepository;
        this.notificationTermRepository = notificationTermRepository;
        this.userSession = userSession;
    }


    public void verifyTermAccepted(String username){
        log.info("Buscando pela atual versao do termo");
        Term termo = this.termRepository.findByAtualVersao(true);
        log.info("Buscando pelo usuario");
        User user = (User) this.userRepository.findByUsername(username);
        log.info("Buscando pelo termo e pelo usuario");
        StatusTerm statusTerm = this.statusTermoRepository.findByTermoAndUser(termo,user);

        log.info("Validacao do termo de aceite");
        if(statusTerm == null ){
            throw new FirstTimeTermAccepted();
        }else if(statusTerm.getStatus().equals("rejected")) {
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

    public Term termActual(){
        return this.termRepository.findByAtualVersao(true);
    }

    public List<NotificationTerm> notificationTerm(){
        User user = this.userSession.userLogged();
        return this.notificationTermRepository.findByUser(user);
    }

    public void updateNotificationTerm(Integer id){
        NotificationTerm notificationTerm = this.notificationTermRepository.findById(id).orElseThrow();
        notificationTerm.setFlagNotificacao("read");

        this.notificationTermRepository.save(notificationTerm);
    }
}
package com.imagem.backend.services;

import com.imagem.backend.domain.*;
import com.imagem.backend.dtos.TermAcceptedDTO;
import com.imagem.backend.exceptions.FirstTimeTermAccepted;
import com.imagem.backend.exceptions.TermNotAccepted;
import com.imagem.backend.infra.security.UserSession;
import com.imagem.backend.repositories.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class StatusTermService {

    private final StatusTermoRepository statusTermoRepository;

    private final TermRepository termRepository;

    private final UserRepository userRepository;

    private final NotificationTermRepository notificationTermRepository;

    private final UserSession userSession;

    private final TermFunctionRepository termFunctionRepository;

    public StatusTermService(StatusTermoRepository statusTermoRepository, TermRepository termRepository,
                             UserRepository userRepository, NotificationTermRepository notificationTermRepository, UserSession userSession, TermFunctionRepository termFunctionRepository) {
        this.statusTermoRepository = statusTermoRepository;
        this.termRepository = termRepository;
        this.userRepository = userRepository;
        this.notificationTermRepository = notificationTermRepository;
        this.userSession = userSession;
        this.termFunctionRepository = termFunctionRepository;
    }


    public void verifyTermAccepted(String username){
        log.info("Buscando pela atual versao do termo");
        Term termo = this.termRepository.findByAtualVersao(true);
        log.info("Buscando pelo usuario");
        User user = (User) this.userRepository.findByUsername(username);
        log.info("Buscando pelo termo e pelo usuario");
        List<StatusTerm> statusTerm = this.statusTermoRepository.findByTermoAndUser(termo,user);

        log.info("Validacao do termo de aceite");
        if(statusTerm == null ){
            throw new FirstTimeTermAccepted();
        }else if(statusTerm.get(0).getStatus().equals("rejected")) {
            throw new TermNotAccepted();
        }

    }


    public void termAccept(TermAcceptedDTO termAcceptedDTO){
        log.info("Buscando pela atual versao do termo");
        Term termo = this.termRepository.findByAtualVersao(true);
        log.info("Buscando pelo usuario");
        User user = (User) this.userRepository.findByUsername(termAcceptedDTO.username());
        log.info("Buscando pelo termo e pelo usuario");
        List<StatusTerm> statusTerm = this.statusTermoRepository.findByTermoAndUser(termo,user);

        log.info("Associando o termo e a reposta do usuario");

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

    public List<TermFunction> listAllTermFunction(){
        return this.termFunctionRepository.findAll();
    }

    public void updateTermFunction(List<Integer> functionsId, String username, String termRequest){

        User user = (User) this.userRepository.findByUsername(username);
        Term termo = this.termRepository.findByAtualVersao(true);

        List<StatusTerm> statusTerms = this.statusTermoRepository.findByTermoAndUser(termo, user);

        if (functionsId.isEmpty() || termRequest.equals("rejected")) {
            List<StatusTerm> existTerms = this.statusTermoRepository.findByTermoAndUser(termo, user);
            this.statusTermoRepository.deleteAllInBatch(existTerms);
            StatusTerm statusTerm = new StatusTerm();
            statusTerm.setStatus(termRequest);
            statusTerm.setUser(user);
            statusTerm.setTermo(termo);
            this.statusTermoRepository.save(statusTerm);
            return;
        }

        if(statusTerms.isEmpty()){

            for (Integer functionId : functionsId) {
                TermFunction termFunction = new TermFunction();
                StatusTerm statusTerm = new StatusTerm();
                termFunction.setId(functionId);
                statusTerm.setUser(user);
                statusTerm.setTermo(termo);
                statusTerm.setStatus(termRequest);
                statusTerm.setTermoFuncao(termFunction);
                this.statusTermoRepository.save(statusTerm);
            }
            return;
        }

        for (StatusTerm statusTermExist : statusTerms) {
            statusTermExist.setStatus(termRequest);
            this.statusTermoRepository.save(statusTermExist);
        }


        List<Integer> existFunctions = new ArrayList<>();
        for(StatusTerm statusTerm: statusTerms){
            if(statusTerm.getTermoFuncao() != null){
            existFunctions.add(statusTerm.getTermoFuncao().getId());
            }
        }

        for(Integer functionId: existFunctions){
            if(!functionsId.contains(functionId)){
                TermFunction termFunction = new TermFunction();
                termFunction.setId(functionId);

                StatusTerm removeStatusTerm = this.statusTermoRepository.findByTermoAndUserAndTermoFuncao(termo,user,termFunction);
                this.statusTermoRepository.delete(removeStatusTerm);
            }
        }


        for(Integer functionId: functionsId){
            TermFunction termFunction = this.termFunctionRepository.findById(functionId).orElseThrow();

            StatusTerm statusTerm = this.statusTermoRepository.findByTermoAndUserAndTermoFuncao(termo,user,termFunction);
            if(statusTerm == null){
                StatusTerm newStatusTerm = new StatusTerm();
                newStatusTerm.setTermoFuncao(termFunction);
                newStatusTerm.setUser(user);
                newStatusTerm.setTermo(termo);
                newStatusTerm.setStatus(termRequest);
                this.statusTermoRepository.save(newStatusTerm);
            }else{
                statusTerm.setStatus(termRequest);
                this.statusTermoRepository.save(statusTerm);
            }
        }
    }
}
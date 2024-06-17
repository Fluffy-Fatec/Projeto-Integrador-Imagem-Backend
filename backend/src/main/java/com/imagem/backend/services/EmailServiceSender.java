package com.imagem.backend.services;

import com.imagem.backend.domain.Invite;
import com.imagem.backend.domain.User;
import com.imagem.backend.dtos.LogSender;
import com.imagem.backend.dtos.SendInviteRequestDTO;
import com.imagem.backend.dtos.UserLog;
import com.imagem.backend.exceptions.EmailAlreadyInvited;
import com.imagem.backend.exceptions.InviteAlreadySend;
import com.imagem.backend.infra.email.EmailSender;
import com.imagem.backend.infra.ext.LogProducerService;
import com.imagem.backend.infra.security.UserSession;
import com.imagem.backend.repositories.InviteRepository;
import com.imagem.backend.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailServiceSender extends LogProducerService {

    private final InviteRepository inviteRepository;

    private final EmailSender emailSender;

    private final UserRepository userRepository;

    private UserSession userSession;

    public EmailServiceSender(InviteRepository inviteRepository, EmailSender emailSender, UserRepository userRepository, UserSession userSession
                              ) {
        this.inviteRepository = inviteRepository;
        this.emailSender = emailSender;
        this.userRepository = userRepository;
        this.userSession = userSession;
    }


    public void sendInvite(SendInviteRequestDTO dto){

        log.info("Buscando se o email ja existe...");
        if(this.userRepository.existsByEmail(dto.emailInvited())) throw new EmailAlreadyInvited();

        log.info("Buscando o usuário logado...");
        User userLogged = userSession.userLogged();

        LogSender logObject = new LogSender();
        log.info("Tenntativa de enviar convite ao email...");
        try{

            String tokenEmail = emailSender.sendEmail(dto.emailInvited(), userLogged.getEmail());

            log.info("Criando novo registro de convite email...");
            Invite invite = new Invite();
            invite.setEmail(dto.emailInvited());
            invite.setSolicitante(userLogged);
            invite.setTokeninvite(tokenEmail);

            log.info("Salvando novo registro de convite email...");
            inviteRepository.save(invite);

            log.info("Novo registro salvo...");

            logObject.setUsuario(new UserLog(userLogged.getNome(), userLogged.getId()));
            logObject.setRegistro("An invitation has been sent to a new user");
            sendMessage(logObject);

        }catch (Exception e){
            throw new InviteAlreadySend();
        }

    }
}

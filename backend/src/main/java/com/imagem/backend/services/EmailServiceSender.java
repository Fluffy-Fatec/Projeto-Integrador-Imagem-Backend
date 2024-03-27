package com.imagem.backend.services;

import com.imagem.backend.domain.Invite;
import com.imagem.backend.domain.User;
import com.imagem.backend.dtos.SendInviteRequestDTO;
import com.imagem.backend.exceptions.InviteAlreadySend;
import com.imagem.backend.infra.email.EmailSender;
import com.imagem.backend.infra.security.UserSession;
import com.imagem.backend.repositories.InviteRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceSender {

    private final InviteRepository inviteRepository;

    private final EmailSender emailSender;

    private UserSession userSession;

    public EmailServiceSender(InviteRepository inviteRepository, EmailSender emailSender, UserSession userSession) {
        this.inviteRepository = inviteRepository;
        this.emailSender = emailSender;
        this.userSession = userSession;
    }


    public void sendInvite(SendInviteRequestDTO dto){

        User userLogged = userSession.userLogged();

        try{

        String tokenEmail = emailSender.sendEmail(dto.emailInvited(), userLogged.getEmail());

        Invite invite = new Invite();
        invite.setEmail(dto.emailInvited());
        invite.setSolicitante(userLogged);
        invite.setTokeninvite(tokenEmail);

        inviteRepository.save(invite);

        }catch (Exception e){
            throw new InviteAlreadySend();
        }

    }
}

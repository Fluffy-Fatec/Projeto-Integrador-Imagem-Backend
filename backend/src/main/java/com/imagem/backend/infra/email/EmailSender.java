package com.imagem.backend.infra.email;


import com.imagem.backend.exceptions.EmailServiceUnavailable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EmailSender {

    private final JavaMailSender javaMailSender;

    private final String inviteSubject = "Invite";
    public EmailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public String sendEmail(String emailInvited, String userSender){

        String alphanumeric = UUID.randomUUID().toString();

        var message = new SimpleMailMessage();
        message.setFrom(userSender);
        message.setTo(emailInvited);
        message.setSubject(inviteSubject);
        message.setText(alphanumeric);

        try {
            javaMailSender.send(message);
        }catch (Exception e){
            throw new EmailServiceUnavailable();
        }


        return alphanumeric;
    }


}

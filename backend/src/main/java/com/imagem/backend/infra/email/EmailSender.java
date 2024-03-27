package com.imagem.backend.infra.email;



import com.imagem.backend.dtos.EmailTemplate;
import com.imagem.backend.utils.AlphanumericGenerator;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSender {

    private final JavaMailSender javaMailSender;

    private final String inviteSubject = "Invite";
    public EmailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmail(String emailInvited, String userSender){

        var message = new SimpleMailMessage();
        message.setFrom(userSender);
        message.setTo(emailInvited);
        message.setSubject(inviteSubject);
        message.setText(AlphanumericGenerator.generateAlphanumeric());
        javaMailSender.send(message);
    }


}

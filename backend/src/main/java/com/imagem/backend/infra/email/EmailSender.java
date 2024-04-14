package com.imagem.backend.infra.email;


import com.imagem.backend.exceptions.EmailServiceUnavailable;
import com.imagem.backend.utils.EmailModel;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailSender {

    private final JavaMailSender javaMailSender;

    public EmailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public String sendEmail(String emailInvited, String userSender){
        log.info("Preparando o email para ser enviado...");
        try {
            EmailModel emailModel = new EmailModel();

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(userSender);
            helper.setTo(emailInvited);
            helper.setSubject(emailModel.getInviteSubject());
            helper.setText(emailModel.getUniqueString(), true);

            log.info("Enviando o convite ao email...");
            javaMailSender.send(message);
            log.info("Email enviado...");

            return emailModel.getAlphanumeric();
        }catch (Exception e){
            throw new EmailServiceUnavailable();
        }


    }


}

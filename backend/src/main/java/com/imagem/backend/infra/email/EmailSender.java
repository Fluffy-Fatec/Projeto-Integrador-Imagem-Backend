package com.imagem.backend.infra.email;


import com.imagem.backend.exceptions.EmailServiceUnavailable;
import com.imagem.backend.utils.EmailModel;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailSender {

    private final JavaMailSender javaMailSender;

    public EmailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public String sendEmail(String emailInvited, String userSender){

        try {

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(userSender);
            helper.setTo(emailInvited);
            helper.setSubject(EmailModel.getInstance().getInviteSubject());
            helper.setText(EmailModel.getInstance().getUniqueString(), true);

            javaMailSender.send(message);
            return EmailModel.getInstance().getAlphanumeric();
        }catch (Exception e){
            throw new EmailServiceUnavailable();
        }


    }


}

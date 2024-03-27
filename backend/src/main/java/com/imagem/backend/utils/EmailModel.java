package com.imagem.backend.utils;

import java.util.UUID;

public class EmailModel {

    private static EmailModel instance;
    private String uniqueString;

    private String alphanumeric;

    private String inviteSubject;

    private EmailModel() {
        this.inviteSubject = "Invite";
        this.alphanumeric = UUID.randomUUID().toString();
        this.uniqueString = "<!DOCTYPE html>\n" +
                "<html lang=\"pt-br\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Olá, Mundo!</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<h1>Olá, Mundo!</h1>\n" +
                "\n" +
                "<p>Este é um exemplo básico de HTML.</p>\n" +
                "\n" +
                "<p>Confira este <a href=\"https://exemplo.com/"+ alphanumeric +"\">link</a> clicável.</p>\n" +
                "\n" +
                "</body>\n" +
                "</html>";
    }

    public static synchronized EmailModel getInstance() {
        if (instance == null) {
            instance = new EmailModel();
        }
        return instance;
    }

    public String getAlphanumeric() {
        return alphanumeric;
    }

    public String getUniqueString() {
        return uniqueString;
    }

    public String getInviteSubject() {
        return inviteSubject;
    }
}

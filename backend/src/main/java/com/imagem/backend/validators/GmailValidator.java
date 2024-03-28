package com.imagem.backend.utils;

import com.imagem.backend.exceptions.InvalidEmail;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GmailValidator {

    private static final String GMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@gmail\\.com$";
    private static final Pattern pattern = Pattern.compile(GMAIL_REGEX);

    public static void emailValidator(String email){
        Matcher matcher = pattern.matcher(email);
        if(!matcher.matches()) throw new InvalidEmail();
    }

}

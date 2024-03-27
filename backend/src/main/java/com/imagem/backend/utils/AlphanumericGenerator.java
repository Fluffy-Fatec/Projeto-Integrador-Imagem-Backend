package com.imagem.backend.utils;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class AlphanumericGenerator {

    private static final String ALPHANUMERIC = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz";
    private static final Integer length = 10;
    private static final SecureRandom random = new SecureRandom();

    public static String generateAlphanumeric() {
        char[] alphanumericChars = new char[length];
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(ALPHANUMERIC.length());
            alphanumericChars[i] = ALPHANUMERIC.charAt(randomIndex);
        }
        return new String(alphanumericChars);
    }
}

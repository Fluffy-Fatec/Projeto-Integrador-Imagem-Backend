package com.imagem.backend.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.imagem.backend.domain.User;
import com.imagem.backend.dtos.LogSender;
import com.imagem.backend.dtos.LoginResponseDTO;
import com.imagem.backend.dtos.UserLog;
import com.imagem.backend.infra.ext.LogProducerService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService extends LogProducerService {

    private final String secret = "717ee184-8648-4d5e-b8c7-049ef21119e7";

    public LoginResponseDTO generateToken(User user){
        LogSender logObject = new LogSender();
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            logObject.setUsuario(new UserLog(user.getNome(), user.getId()));
            logObject.setRegistro("The user has successfully logged in");
            sendMessage(logObject);
            return new LoginResponseDTO(JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(user.getUsername())
                    .withExpiresAt(generateExpirationDate())
                    .sign(algorithm), user.getRole().getRole());

        }catch(JWTCreationException e){
            logObject.setUsuario(new UserLog(user.getNome(), user.getId()));
            logObject.setRegistro("The user attempted to log in and failed to generate the token");
            sendMessage(logObject);
            throw new RuntimeException("Erro ao gerar token", e);

        }

    }

    public String validateToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception){
            return "";
        }
    }


    private Instant generateExpirationDate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}

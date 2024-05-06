package com.imagem.backend.infra;

import com.imagem.backend.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserAlreadyExistException.class)
    private ResponseEntity<RestErrorMessage> userAlreadyExist(UserAlreadyExistException exception){
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR,exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(threatResponse);
    }

    @ExceptionHandler(UserCreateRequestNotValidException.class)
    private ResponseEntity<RestErrorMessage> userAlreadyExist(UserCreateRequestNotValidException exception){
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.BAD_REQUEST,exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(threatResponse);
    }
    @ExceptionHandler(EmailServiceUnavailable.class)
    private ResponseEntity<RestErrorMessage> userEmailUnavailable(EmailServiceUnavailable exception){
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.BAD_REQUEST,exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(threatResponse);
    }
    @ExceptionHandler(InviteAlreadySend.class)
    private ResponseEntity<RestErrorMessage> inviteAlreadySend(InviteAlreadySend exception){
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.BAD_REQUEST,exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(threatResponse);
    }
    @ExceptionHandler(UserNotAuthenticated.class)
    private ResponseEntity<RestErrorMessage> userNotAuthenticated(UserNotAuthenticated exception){
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.BAD_REQUEST,exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(threatResponse);
    }

    @ExceptionHandler(InvalidEmail.class)
    private ResponseEntity<RestErrorMessage> invalidEmail(InvalidEmail exception){
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.BAD_REQUEST,exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(threatResponse);
    }

    @ExceptionHandler(NotInvited.class)
    private ResponseEntity<RestErrorMessage> notInvited(NotInvited exception){
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.NOT_FOUND,exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(threatResponse);
    }

    @ExceptionHandler(InvalidCelphone.class)
    private ResponseEntity<RestErrorMessage> invalidCelphone(InvalidCelphone exception){
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.BAD_REQUEST,exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(threatResponse);
    }

    @ExceptionHandler(InvalidCpf.class)
    private ResponseEntity<RestErrorMessage> invalidCpf(InvalidCpf exception){
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.BAD_REQUEST,exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(threatResponse);
    }

    @ExceptionHandler(InvalidPassword.class)
    private ResponseEntity<RestErrorMessage> invalidPassword(InvalidPassword exception){
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.BAD_REQUEST,exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(threatResponse);
    }

    @ExceptionHandler(LoginFailed.class)
    private ResponseEntity<RestErrorMessage> invalidPassword(LoginFailed exception){
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.FORBIDDEN,exception.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(threatResponse);
    }

    @ExceptionHandler(EmailAlreadyInvited.class)
    private ResponseEntity<RestErrorMessage> emailAlreadyInvited(EmailAlreadyInvited exception){
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.BAD_REQUEST,exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(threatResponse);
    }
    @ExceptionHandler(UserNotExist.class)
    private ResponseEntity<RestErrorMessage> emailAlreadyInvited(UserNotExist exception){
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.BAD_REQUEST,exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(threatResponse);
    }

    @ExceptionHandler(TermNotAccepted.class)
    private ResponseEntity<RestErrorMessage> termNotAccepted(TermNotAccepted exception){
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.FORBIDDEN,exception.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(threatResponse);
    }

    @ExceptionHandler(FirstTimeTermAccepted.class)
    private ResponseEntity<RestErrorMessage> firstTimeAccepted(FirstTimeTermAccepted exception){
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.FORBIDDEN,exception.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(threatResponse);
    }



}
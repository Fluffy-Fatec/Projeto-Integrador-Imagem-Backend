package com.imagem.backend.controllers;


import com.imagem.backend.domain.User;
import com.imagem.backend.dtos.*;
import com.imagem.backend.infra.security.TokenService;
import com.imagem.backend.services.EmailServiceSender;
import com.imagem.backend.services.UserService;
import com.imagem.backend.utils.GmailValidator;
import com.imagem.backend.validators.UserServiceValidator;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class UserController {

    private final AuthenticationManager authenticationManager;

    private final UserService userService;

    private final TokenService tokenService;

    private final UserServiceValidator userServiceValidator;

    private final EmailServiceSender emailServiceSender;

    public UserController(AuthenticationManager authenticationManager, UserService userService, TokenService tokenService, UserServiceValidator userServiceValidator, EmailServiceSender emailServiceSender) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.tokenService = tokenService;
        this.userServiceValidator = userServiceValidator;
        this.emailServiceSender = emailServiceSender;
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.username(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register/{id}")
    public ResponseEntity<RegisterResponseDTO> register(@PathVariable("id") UUID tokenAlphanumeric,
                                                        @RequestBody @Valid RegisterDTO data){
        this. userServiceValidator.apply(data);
        this.userService.save(data,
                tokenAlphanumeric.toString());

        return ResponseEntity.ok().body(new RegisterResponseDTO("conta criada com sucesso"));
    }

    // Metodo simples para gerar usuario pela primeira , apagar quando for subir para main
    @PostMapping("/register/adm")
    public ResponseEntity<RegisterResponseDTO> registerAdm(@RequestBody @Valid RegisterDTO data){
        this.userService.saveAdm(data);

        return ResponseEntity.ok().body(new RegisterResponseDTO("conta criada com sucesso"));
    }

    @PostMapping("/invite")
    public ResponseEntity inviteUser(@RequestBody @Valid SendInviteRequestDTO sendInviteRequestDTO){

        GmailValidator.emailValidator(sendInviteRequestDTO.emailInvited());

        this.emailServiceSender.sendInvite(sendInviteRequestDTO);
        return ResponseEntity.ok().body("email enviado!");
    }

}

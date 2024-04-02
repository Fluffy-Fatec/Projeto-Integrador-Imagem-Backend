package com.imagem.backend.controllers;


import com.imagem.backend.domain.FieldChange;
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

import java.util.List;
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

        LoginResponseDTO token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(token);
    }

    @PostMapping("/register/{id}")
    public ResponseEntity<GlobalResponseDTO> register(@PathVariable("id") UUID tokenAlphanumeric,
                                                        @RequestBody @Valid RegisterDTO data){
        this.userServiceValidator.apply(data);
        this.userService.save(data,
                tokenAlphanumeric.toString());

        return ResponseEntity.ok().body(new GlobalResponseDTO("conta criada com sucesso"));
    }

    // Metodo simples para gerar usuario pela primeira , apagar quando for subir para main
    @PostMapping("/register/adm")
    public ResponseEntity<GlobalResponseDTO> registerAdm(@RequestBody @Valid RegisterDTO data){
        this.userService.saveAdm(data);

        return ResponseEntity.ok().body(new GlobalResponseDTO("conta criada com sucesso"));
    }

    @PostMapping("/invite")
    public ResponseEntity<GlobalResponseDTO> inviteUser(@RequestBody @Valid SendInviteRequestDTO sendInviteRequestDTO){

        GmailValidator.emailValidator(sendInviteRequestDTO.emailInvited());

        this.emailServiceSender.sendInvite(sendInviteRequestDTO);
        return ResponseEntity.ok().body(new GlobalResponseDTO("Convite enviado com sucesso!"));
    }

    @PutMapping("/update/pass")
    public ResponseEntity<GlobalResponseDTO> updatePass(@RequestBody @Valid UpdatePassRequestDTO updatePassRequestDTO){

        this.userServiceValidator.validPassword(updatePassRequestDTO.password());
        this.userService.updpatePassUser(updatePassRequestDTO);

        return ResponseEntity.ok().body(new GlobalResponseDTO("Atualização do usuário com sucesso!"));
    }

    @PutMapping("/update/user")
    public ResponseEntity updateUser(@RequestBody @Valid UpdateUserRequestDTO data){

        this.userServiceValidator.validCelphone(data.celular());
        this.userServiceValidator.validCpf(data.cpf());
        GmailValidator.emailValidator(data.email());
        this.userService.updateUser(data);

        return ResponseEntity.ok().body(new GlobalResponseDTO("Ususário foi atualizado"));
    }

    @PutMapping("/update/user/approve")
    public ResponseEntity updateUserApprove(@RequestBody @Valid UserUpdateApproveRequestDTO data){

        try {
            this.userService.updateUserToApprove(data);
        }catch (Exception e){

        }

        return ResponseEntity.ok().body(new GlobalResponseDTO("Atualizacao foi aceita foi atualizado"));
    }

    @GetMapping("/update/user/list")
    public ResponseEntity<List<RespondeListFieldChangeDTO>> listUpdateUserApprove(){

        List<RespondeListFieldChangeDTO> listSolicitaation= this.userService.listUpdateSolicitaions();

        return ResponseEntity.ok().body(listSolicitaation);
    }

}


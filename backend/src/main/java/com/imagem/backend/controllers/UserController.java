package com.imagem.backend.controllers;


import com.imagem.backend.domain.ENUM.UserRole;
import com.imagem.backend.domain.FieldChange;
import com.imagem.backend.domain.Notification;
import com.imagem.backend.domain.User;
import com.imagem.backend.dtos.*;
import com.imagem.backend.infra.security.TokenService;
import com.imagem.backend.services.EmailServiceSender;
import com.imagem.backend.services.StatusTermService;
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

    private final StatusTermService statusTermService;

    public UserController(AuthenticationManager authenticationManager, UserService userService, TokenService tokenService, UserServiceValidator userServiceValidator, EmailServiceSender emailServiceSender, StatusTermService statusTermService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.tokenService = tokenService;
        this.userServiceValidator = userServiceValidator;
        this.emailServiceSender = emailServiceSender;
        this.statusTermService = statusTermService;
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.username(), data.password());

        var auth = this.authenticationManager.authenticate(usernamePassword);

        this.statusTermService.verifyTermAccepted(data.username());
        LoginResponseDTO token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(token);
    }

    @PostMapping("/user/term")
    public ResponseEntity termAccept(@RequestBody TermAcceptedDTO termAcceptedDTO) {
        this.statusTermService.termAccept(termAcceptedDTO);
        return ResponseEntity.ok().body(new GlobalResponseDTO("Termo atualizado com sucesso!"));
    }

    @PostMapping("/register/{id}")
    public ResponseEntity<GlobalResponseDTO> register(@PathVariable("id") UUID tokenAlphanumeric,
                                                        @RequestBody @Valid RegisterDTO data){
        this.userServiceValidator.apply(data);
        this.userService.save(data,
                tokenAlphanumeric.toString());

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

    @GetMapping("/list/user")
    public ResponseEntity<List<ListUsersResponseDTO>> listAllUsers(){

        List<ListUsersResponseDTO> listUsersResponseDTO = this.userService.listAllUsers();

        return ResponseEntity.ok().body(listUsersResponseDTO);
    }

    @GetMapping("/list/user/{id}")
    public ResponseEntity<ListUsersResponseDTO> listUser(@PathVariable("id") Integer id){

        ListUsersResponseDTO listUsersResponseDTO = this.userService.listUser(id);

        return ResponseEntity.ok().body(listUsersResponseDTO);
    }


    @DeleteMapping("/delete/user/{id}")
    public ResponseEntity daleteUser(@PathVariable("id") Integer id) {

        this.userService.deleteUser(id);
        return ResponseEntity.ok().body(new GlobalResponseDTO("Usuário deletado"));
    }

    @PutMapping("/update/user/role")
    public ResponseEntity updateUserRole(@RequestBody UpdateUserRoleRequestDTO roleRequestDTO){

        this.userService.updateRole(roleRequestDTO);

        return ResponseEntity.ok().body(new GlobalResponseDTO("Atualizacao de acesso realizada"));
    }

    @GetMapping("/user/logged")
    public ResponseEntity<UpdateUserRequestDTO> listUser(){

        UpdateUserRequestDTO updateUserRequestDTO = this.userService.userLogged();

        return ResponseEntity.ok().body(updateUserRequestDTO);
    }

    @GetMapping("/field/notification")
    public ResponseEntity<List<ResponseNotificationDTO>> notificationFieldChange(){
        List<ResponseNotificationDTO> notification = this.userService.notificationFieldChange();

        return ResponseEntity.ok().body(notification);
    }
}
package com.imagem.backend.controllers;


import com.imagem.backend.domain.User;
import com.imagem.backend.dtos.*;
import com.imagem.backend.infra.security.TokenService;
import com.imagem.backend.services.UserService;
import com.imagem.backend.validators.UserServiceValidator;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UserController {

    private final AuthenticationManager authenticationManager;

    private final UserService userService;

    private final TokenService tokenService;

    private final UserServiceValidator userServiceValidator;

    public UserController(AuthenticationManager authenticationManager, UserService userService, TokenService tokenService, UserServiceValidator userServiceValidator) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.tokenService = tokenService;
        this.userServiceValidator = userServiceValidator;
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.username(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(@RequestBody @Valid RegisterDTO data){
        this. userServiceValidator.apply(data);
        this.userService.save(data);

        return ResponseEntity.ok().body(new RegisterResponseDTO("conta criada com sucesso"));
    }

}

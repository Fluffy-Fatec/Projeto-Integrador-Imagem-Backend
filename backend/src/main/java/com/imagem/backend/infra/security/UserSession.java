package com.imagem.backend.infra.security;

import com.imagem.backend.domain.User;
import com.imagem.backend.exceptions.UserNotAuthenticated;
import com.imagem.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserSession {

    private final UserRepository userRepository;

    @Autowired
    public UserSession(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User userLogged() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.getPrincipal() == null) throw new UserNotAuthenticated();
        User user = (User) authentication.getPrincipal();

        return user;
    }
}
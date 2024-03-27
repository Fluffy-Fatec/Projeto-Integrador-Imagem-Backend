package com.imagem.backend.services;

import com.imagem.backend.domain.ENUM.UserRole;
import com.imagem.backend.domain.Invite;
import com.imagem.backend.domain.User;
import com.imagem.backend.dtos.RegisterDTO;
import com.imagem.backend.exceptions.NotInvited;
import com.imagem.backend.exceptions.UserAlreadyExistException;
import com.imagem.backend.repositories.InviteRepository;
import com.imagem.backend.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final InviteRepository inviteRepository;

    public UserService(UserRepository userRepository, InviteRepository inviteRepository) {
        this.userRepository = userRepository;
        this.inviteRepository = inviteRepository;
    }


    public void save(RegisterDTO dto, String tokenInvite) {

        if(this.userRepository.findByUsername(dto.username()) != null) throw new UserAlreadyExistException();

        Invite invite = inviteRepository.findBytokeninvite(tokenInvite);

        if(invite == null) throw new NotInvited();

        String encryptedPassword = new BCryptPasswordEncoder().encode(dto.password());

        User newUser = new User();
        newUser.setUsername(dto.username());
        newUser.setRole(UserRole.USER);
        newUser.setPassword(encryptedPassword);
        newUser.setCpf(dto.cpf());
        newUser.setNome(dto.nome());
        newUser.setEmail(invite.getEmail());
        newUser.setCelular(dto.celular());

        this.userRepository.save(newUser);
    }
}

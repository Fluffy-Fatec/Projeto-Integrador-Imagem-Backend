package com.imagem.backend.services;

import com.imagem.backend.domain.User;
import com.imagem.backend.dtos.RegisterDTO;
import com.imagem.backend.exceptions.UserAlreadyExistException;
import com.imagem.backend.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> listAllUsers(){
        return this.userRepository.findAll();
    }

    public UserDetails showByUsername(String username){
        return this.userRepository.findByUsername(username);
    }

    public void save(RegisterDTO dto) {

        if(this.userRepository.findByUsername(dto.username()) != null) throw new UserAlreadyExistException();

        String encryptedPassword = new BCryptPasswordEncoder().encode(dto.password());
        User newUser = new User(dto.username(), encryptedPassword, dto.role());

        this.userRepository.save(newUser);
    }
}

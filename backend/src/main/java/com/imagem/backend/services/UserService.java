package com.imagem.backend.services;

import com.imagem.backend.domain.ENUM.UserRole;
import com.imagem.backend.domain.Invite;
import com.imagem.backend.domain.User;
import com.imagem.backend.dtos.RegisterDTO;
import com.imagem.backend.dtos.UpdatePassRequestDTO;
import com.imagem.backend.exceptions.NotInvited;
import com.imagem.backend.exceptions.UserAlreadyExistException;
import com.imagem.backend.infra.security.UserSession;
import com.imagem.backend.repositories.InviteRepository;
import com.imagem.backend.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final InviteRepository inviteRepository;

    private final UserSession userSession;

    public UserService(UserRepository userRepository, InviteRepository inviteRepository, UserSession userSession) {
        this.userRepository = userRepository;
        this.inviteRepository = inviteRepository;
        this.userSession = userSession;
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


    // Metodo simples para gerar usuario pela primeira , apagar quando for subir para main
    public void saveAdm(RegisterDTO dto) {

        String encryptedPassword = new BCryptPasswordEncoder().encode(dto.password());

        User newUser = new User();
        newUser.setUsername(dto.username());
        newUser.setRole(UserRole.ADMIN);
        newUser.setPassword(encryptedPassword);
        newUser.setCpf(dto.cpf());
        newUser.setNome(dto.nome());
        newUser.setEmail("emailteste@gmail.com");
        newUser.setCelular(dto.celular());

        this.userRepository.save(newUser);
    }

    public void updpatePassUser(UpdatePassRequestDTO updatePassRequestDTO){

        User userLogged = userSession.userLogged();
        User user = (User) this.userRepository.findByUsername(userLogged.getUsername());

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String passEncoded = encoder.encode(updatePassRequestDTO.password());
        user.setPassword(passEncoded);

        this.userRepository.save(user);

    }
}

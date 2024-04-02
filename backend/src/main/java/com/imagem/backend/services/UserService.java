package com.imagem.backend.services;

import com.imagem.backend.domain.ENUM.StatusFieldChange;
import com.imagem.backend.domain.ENUM.UserRole;
import com.imagem.backend.domain.FieldChange;
import com.imagem.backend.domain.Invite;
import com.imagem.backend.domain.User;
import com.imagem.backend.dtos.RegisterDTO;
import com.imagem.backend.dtos.UpdatePassRequestDTO;
import com.imagem.backend.dtos.UpdateUserRequestDTO;
import com.imagem.backend.exceptions.NotInvited;
import com.imagem.backend.exceptions.UserAlreadyExistException;
import com.imagem.backend.infra.security.UserSession;
import com.imagem.backend.repositories.FieldChangeRepository;
import com.imagem.backend.repositories.InviteRepository;
import com.imagem.backend.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;

    private final InviteRepository inviteRepository;

    private final UserSession userSession;

    private final FieldChangeRepository fieldChangeRepository;
    public UserService(UserRepository userRepository, InviteRepository inviteRepository, UserSession userSession, FieldChangeRepository fieldChangeRepository) {
        this.userRepository = userRepository;
        this.inviteRepository = inviteRepository;
        this.userSession = userSession;
        this.fieldChangeRepository = fieldChangeRepository;
    }


    public void save(RegisterDTO dto, String tokenInvite) {
        log.info("Buscando usuário pelo nome de usuário...");

        if(this.userRepository.findByUsername(dto.username()) != null) throw new UserAlreadyExistException();

        log.info("Verificando se existe algum convite...");

        Invite invite = inviteRepository.findBytokeninvite(tokenInvite);

        if(invite == null) throw new NotInvited();

        log.info("Encriptando a senha do novo usuário...");
        String encryptedPassword = new BCryptPasswordEncoder().encode(dto.password());

        log.info("Criando novo usuário...");
        User newUser = new User();
        newUser.setUsername(dto.username());
        newUser.setRole(UserRole.USER);
        newUser.setPassword(encryptedPassword);
        newUser.setCpf(dto.cpf());
        newUser.setNome(dto.nome());
        newUser.setEmail(invite.getEmail());
        newUser.setCelular(dto.celular());

        log.info("Salvando novo usuário...");
        this.userRepository.save(newUser);

        log.info("Deletando o convite utilizado pelo usuário...");
        this.inviteRepository.delete(invite);
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
        log.info("Buscando os dados do usuário logado...");
        User userLogged = userSession.userLogged();

        log.info("Buscando o usuário logado na base...");
        User user = (User) this.userRepository.findByUsername(userLogged.getUsername());

        log.info("Encriptando a nova senha do usuário...");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String passEncoded = encoder.encode(updatePassRequestDTO.password());
        user.setPassword(passEncoded);

        log.info("Salvando a nova senha do usuário...");
        this.userRepository.save(user);
        log.info("Salva a nova senha do usuário...");

    }

    public void updateUser(UpdateUserRequestDTO dto){

        User userLogged = userSession.userLogged();

        User user = userRepository.findById(Long.valueOf(userLogged.getId())).orElseThrow();

        if(user.getRole() == UserRole.ADMIN) {

            user.setUsername(dto.username());
            user.setCpf(dto.cpf());
            user.setNome(dto.nome());
            user.setEmail(dto.email());
            user.setCelular(dto.celular());

            userRepository.save(user);
        }else{
            FieldChange fieldChange = new FieldChange();

            fieldChange.setUser(user);
            fieldChange.setNovoCelular(dto.celular());
            fieldChange.setNovoCpf(dto.cpf());
            fieldChange.setNovoEmail(dto.email());
            fieldChange.setNovoUsername(dto.username());
            fieldChange.setStatus(StatusFieldChange.PENDENTE.getStatus());

            fieldChangeRepository.save(fieldChange);
        }
    }
}

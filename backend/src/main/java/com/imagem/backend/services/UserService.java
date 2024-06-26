package com.imagem.backend.services;

import com.imagem.backend.domain.*;
import com.imagem.backend.domain.ENUM.FlagNotificacaoEnum;
import com.imagem.backend.domain.ENUM.StatusFieldChange;
import com.imagem.backend.domain.ENUM.UserRole;
import com.imagem.backend.dtos.*;
import com.imagem.backend.exceptions.NotInvited;
import com.imagem.backend.exceptions.UserAlreadyExistException;
import com.imagem.backend.exceptions.UserNotAuthenticated;
import com.imagem.backend.exceptions.UserNotExist;
import com.imagem.backend.infra.ext.BlacklistFactory;
import com.imagem.backend.infra.ext.LogProducerService;
import com.imagem.backend.infra.security.UserSession;
import com.imagem.backend.repositories.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class UserService extends LogProducerService {

    private final UserRepository userRepository;

    private final InviteRepository inviteRepository;

    private final UserSession userSession;

    private final FieldChangeRepository fieldChangeRepository;

    private final NotificationRepository notificationRepository;

    private final BlacklistFactory blacklistFactory;

    private final StatusTermoRepository statusTermoRepository;

    private final NotificationTermRepository notificationTermRepository;

    public UserService(UserRepository userRepository, InviteRepository inviteRepository, UserSession userSession,
                       FieldChangeRepository fieldChangeRepository, NotificationRepository notificationRepository,
                       BlacklistFactory blacklistFactory, StatusTermoRepository statusTermoRepository, NotificationTermRepository notificationTermRepository
                       ) {
        this.userRepository = userRepository;
        this.inviteRepository = inviteRepository;
        this.userSession = userSession;
        this.fieldChangeRepository = fieldChangeRepository;
        this.notificationRepository = notificationRepository;
        this.blacklistFactory = blacklistFactory;
        this.statusTermoRepository = statusTermoRepository;
        this.notificationTermRepository = notificationTermRepository;
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
        User user = this.userRepository.saveAndFlush(newUser);

        log.info("Deletando o convite utilizado pelo usuário...");
        LogSender logObject = new LogSender();
        logObject.setUsuario(new UserLog(newUser.getNome(), user.getId()));
        logObject.setRegistro("A new user has been created");
        sendMessage(logObject);

        // this.inviteRepository.delete(invite);
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

        LogSender logObject = new LogSender();
        logObject.setUsuario(new UserLog(user.getNome(), user.getId()));
        logObject.setRegistro("The user has successfully changed the password");
        sendMessage(logObject);

    }

    public void updateUser(UpdateUserRequestDTO dto){

        log.info("Buscando os dados do usuário logado...");
        User userLogged = userSession.userLogged();

        log.info("Buscando o usuário logado na base...");
        User user = userRepository.findById(userLogged.getId()).orElseThrow();

        LogSender logObject = new LogSender();
        logObject.setUsuario(new UserLog(user.getNome(), user.getId()));

        log.info("Verificando a role do usuario...");
        if(user.getRole() == UserRole.ADMIN) {

            log.info("Usuário com a role admin...");
            user.setUsername(dto.username());
            user.setCpf(dto.cpf());
            user.setNome(dto.nome());
            user.setEmail(dto.email());
            user.setCelular(dto.celular());

            log.info("Alteração do usuário sendo realizada...");
            userRepository.save(user);
            logObject.setRegistro("The administrator changes your data");
            log.info("Alteração do usuário foi realizada...");
        }else{
            log.info("Usuário com a role user...");

            log.info("Preparando a solicitação de ateração dos campos...");
            FieldChange fieldChange = new FieldChange();

            fieldChange.setUser(user);
            fieldChange.setNovocelular(dto.celular());
            fieldChange.setNovocpf(dto.cpf());
            fieldChange.setNovoemail(dto.email());
            fieldChange.setNovousername(dto.username());
            fieldChange.setNovonome(dto.nome());
            fieldChange.setStatus(StatusFieldChange.PENDENTE.getStatus());

            log.info("Solicitação de ateração dos campos sendo enviada...");
            fieldChangeRepository.save(fieldChange);
            logObject.setRegistro("The user requested to change their data");

            log.info("Solicitação de ateração dos campos enviada...");
        }

        sendMessage(logObject);

    }

    public void updateUserToApprove(UserUpdateApproveRequestDTO dto) throws ParseException {

        log.info("Buscando os dados do usuario logado...");
        User userLogged = userSession.userLogged();

        log.info("Verificando o tempo do servidor...");
        Timestamp timestampAtual = new Timestamp(System.currentTimeMillis());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String formattedTimestampString = sdf.format(new Date(timestampAtual.getTime()));

        Date parsedDate = sdf.parse(formattedTimestampString);
        Timestamp formattedTimestamp = new Timestamp(parsedDate.getTime());


        LogSender logObject = new LogSender();
        logObject.setUsuario(new UserLog(userLogged.getNome(), userLogged.getId()));

        log.info("Verificando o status da atualizacao...");
        if(dto.approve().equals(StatusFieldChange.APROVADO.getStatus())){

            log.info("Aprovando a atualizacao...");
            FieldChange fieldChange = fieldChangeRepository.findById(dto.id()).orElseThrow();
            fieldChange.setStatus(StatusFieldChange.APROVADO.getStatus());
            fieldChange.setDataAprovacao(formattedTimestamp);
            fieldChange.setAdmin(userLogged);

            log.info("Alterando o usuario...");
            User userUpdate = fieldChange.getUser();
            userUpdate.setCelular(fieldChange.getNovocelular());
            userUpdate.setEmail(fieldChange.getNovoemail());
            userUpdate.setCpf(fieldChange.getNovocpf());
            userUpdate.setNome(fieldChange.getNovonome());
            userUpdate.setUsername(fieldChange.getNovousername());

            log.info("Salvando o usuario...");
            userRepository.save(userUpdate);
            log.info("Usuario salvo...");

            log.info("Salvando a alteracao...");
            fieldChangeRepository.save(fieldChange);

            logObject.setRegistro("Administrator accepts data change successfully");
            sendMessage(logObject);

        }else{

            log.info("Rejeitando a alteracao...");
            FieldChange fieldChange = fieldChangeRepository.findById(dto.id()).orElseThrow();
            fieldChange.setStatus(StatusFieldChange.REJEITADO.getStatus());
            fieldChange.setDataRejeicao(timestampAtual);
            fieldChange.setAdmin(userLogged);

            log.info("Salvando a rejeicao da alteracao...");
            fieldChangeRepository.save(fieldChange);

            logObject.setRegistro("Administrator rejects data change successfully");
            sendMessage(logObject);
        }

    }

    public List<RespondeListFieldChangeDTO> listUpdateSolicitaions(){
        log.info("Buscando todas as solicitações de update...");
        List<FieldChange> listFieldChanges =  this.fieldChangeRepository.findAll();

        List<RespondeListFieldChangeDTO> listUpdateFieldChange = new ArrayList<>();

        for (FieldChange fieldChange: listFieldChanges) {
            if (fieldChange.getUser() != null) {

                log.info("Setando o retorno do usuario...");
                UserFieldChangeResponseDTO userFieldChangeResponseDTO = new UserFieldChangeResponseDTO(
                        fieldChange.getUser().getId(),
                        fieldChange.getUser().getUsername(),
                        fieldChange.getUser().getNome(),
                        fieldChange.getUser().getEmail(),
                        fieldChange.getUser().getCelular(),
                        fieldChange.getUser().getCpf()

                );
                log.info("Setando o retorno do adm...");

                UserFieldChangeResponseDTO adminFieldChangeResponseDTO;

                if (fieldChange.getStatus().equals(StatusFieldChange.PENDENTE)) {
                    UserFieldChangeResponseDTO adminnFieldChangeResponseDTO = new UserFieldChangeResponseDTO(
                            fieldChange.getAdmin().getId(),
                            fieldChange.getAdmin().getUsername(),
                            fieldChange.getAdmin().getNome(),
                            fieldChange.getAdmin().getEmail(),
                            fieldChange.getAdmin().getCelular(),
                            fieldChange.getAdmin().getCpf()
                    );

                    adminFieldChangeResponseDTO = adminnFieldChangeResponseDTO;

                } else {
                    adminFieldChangeResponseDTO = null;

                }
                log.info("Setando o retorno do da solicitacao...");

                RespondeListFieldChangeDTO fieldChangeDTO = new RespondeListFieldChangeDTO(
                        fieldChange.getId(),
                        adminFieldChangeResponseDTO,
                        userFieldChangeResponseDTO,
                        fieldChange.getNovousername(),
                        fieldChange.getNovonome(),
                        fieldChange.getNovoemail(),
                        fieldChange.getNovocelular(),
                        fieldChange.getNovocpf(),
                        fieldChange.getStatus(),
                        fieldChange.getDataAprovacao(),
                        fieldChange.getDataRejeicao()
                );

                log.info("Adicionando na lilsta de retorno...");
                listUpdateFieldChange.add(fieldChangeDTO);
            }
        }
        return listUpdateFieldChange;
    }

    public List<ListUsersResponseDTO> listAllUsers(){

        log.info("Buscando por todos os usuarios...");
        List<User> listUser = this.userRepository.findAll();

        List<ListUsersResponseDTO> listUsersResponseDTO = new ArrayList<>();

        log.info("Preparando o response do metodo...");
        for(User user: listUser){
            ListUsersResponseDTO usersResponseDTO = new ListUsersResponseDTO();
            usersResponseDTO.setId(user.getId());
            usersResponseDTO.setName(user.getNome());
            usersResponseDTO.setEmail(user.getEmail());
            usersResponseDTO.setUserRole(user.getRole());
            usersResponseDTO.setCreation_date(user.getCreationdate());

            listUsersResponseDTO.add(usersResponseDTO);
        }

        return  listUsersResponseDTO;
    }
    public ListUsersResponseDTO listUser(Integer id){

        log.info("Buscando pelo id do usuario...");
        User listUser = this.userRepository.findById(id).orElse(null);

        if(listUser == null){
            throw new UserNotExist();
        }

        log.info("Preparando o response do metodo...");
        ListUsersResponseDTO usersResponseDTO = new ListUsersResponseDTO();

        usersResponseDTO.setId(listUser.getId());
        usersResponseDTO.setName(listUser.getNome());
        usersResponseDTO.setEmail(listUser.getEmail());
        usersResponseDTO.setUserRole(listUser.getRole());
        usersResponseDTO.setCreation_date(listUser.getCreationdate());

        User user = userSession.userLogged();
        LogSender logObject = new LogSender();
        logObject.setUsuario(new UserLog(user.getNome(), user.getId()));
        logObject.setRegistro("The user requested user data with id =" + listUser.getId());
        sendMessage(logObject);

        return usersResponseDTO;
    }
    public void deleteUser(Integer id){

        log.info("Buscando pelo id do usuario...");
        User user = this.userRepository.findById(id).orElse(null);

        log.info("Buscando os registros de review...");
        if(user == null){
            throw new UserNotAuthenticated();
        }

        List<FieldChange> listFieldChange = user.getUserField();

        if(listFieldChange != null){
            for(FieldChange fieldChange: listFieldChange){
                fieldChange.setUser(null);
                fieldChangeRepository.saveAndFlush(fieldChange);
            }
        }

        if(user.getStatusTerm() != null){
            for(StatusTerm statusTerm: user.getStatusTerm()){
                statusTerm.setUser(null);
                statusTermoRepository.saveAndFlush(statusTerm);
            }
        }

        if(user.getInvites() != null){
            for(Invite invite: user.getInvites()){
                invite.setSolicitante(null);
                inviteRepository.saveAndFlush(invite);
            }
        }

        if(user.getNotifications() != null){
            for(Notification notification: user.getNotifications()){
                notification.setUser(null);
                notificationRepository.saveAndFlush(notification);
            }
        }

        if(user.getNotificationTerms() != null){
            for(NotificationTerm notification: user.getNotificationTerms()){
                notification.setUser(null);
                notificationTermRepository.saveAndFlush(notification);
            }
        }

        this.userRepository.delete(user);

        this.blacklistFactory.salvar(id);

        User userlogged = userSession.userLogged();

        LogSender logObject = new LogSender();
        logObject.setUsuario(new UserLog(userlogged.getNome(), userlogged.getId()));
        logObject.setRegistro("The user with id = "+ id+ " was deleted");
        sendMessage(logObject);
    }
    public void updateRole(UpdateUserRoleRequestDTO roleRequestDTO){

        log.info("Buscando pelo id do usuario...");
        User user = this.userRepository.findById(roleRequestDTO.id()).orElseThrow();

        log.info("Verifica a nova role do usuario...");
        if(roleRequestDTO.role().equals(UserRole.USER.getRole())){
            log.info("Nova role de user do usuario...");
            user.setRole(UserRole.USER);
        }else{
            log.info("Nova role de admin do usuario...");
            user.setRole(UserRole.ADMIN);
        }
        this.userRepository.save(user);

        User userlogged = userSession.userLogged();
        LogSender logObject = new LogSender();
        logObject.setUsuario(new UserLog(userlogged.getNome(), userlogged.getId()));
        logObject.setRegistro("The user role was changed with id = " + user.getId());
        sendMessage(logObject);
    }

    public UpdateUserRequestDTO userLogged(){
        log.info("Buscando os dados do usuário logado...");
        User userLogged = userSession.userLogged();
        return new UpdateUserRequestDTO(
                userLogged.getUsername(),
                userLogged.getEmail(),
                userLogged.getNome(),
                userLogged.getCelular(),
                userLogged.getCpf()
        );
    }

    public List<ResponseNotificationDTO> notificationFieldChange(){
        log.info("Buscando usuário Logado...");
        User userLogged = userSession.userLogged();

        List<ResponseNotificationDTO> responseNotificationDTO = new ArrayList<>();

        log.info("Verificando a role do usuário...");
        if(userLogged.getRole().equals(UserRole.USER)){
            List<Notification> notifications = this.notificationRepository.findByUserAndTipoNotificacao(userLogged, "Usuario");

            for(Notification notification: notifications){
                ResponseNotificationDTO responseNotification = new ResponseNotificationDTO();
                responseNotification.setId(notification.getId());
                responseNotification.setMensagem(notification.getMensagem());
                responseNotification.setFlag_notificacao(notification.getFlagNotificacao());
                responseNotification.setTipo_notificacao(notification.getTipoNotificacao());
                responseNotificationDTO.add(responseNotification);
            }

        }else {
            List<Notification> notifications = this.notificationRepository.findByTipoNotificacao("Admin ");
            for(Notification notification: notifications){
                ResponseNotificationDTO responseNotification = new ResponseNotificationDTO();
                responseNotification.setId(notification.getId());
                responseNotification.setMensagem(notification.getMensagem());
                responseNotification.setFlag_notificacao(notification.getFlagNotificacao());
                responseNotification.setTipo_notificacao(notification.getTipoNotificacao());
                responseNotificationDTO.add(responseNotification);
            }
        }

        return responseNotificationDTO;
    }

    public void updateNotificationField(Integer id){
        Notification notification = this.notificationRepository.findById(id).orElseThrow();

        notification.setFlagNotificacao(FlagNotificacaoEnum.VIST.getStatus());

        User userLogged = userSession.userLogged();
        this.notificationRepository.save(notification);

        LogSender logObject = new LogSender();
        logObject.setUsuario(new UserLog(userLogged.getNome(), userLogged.getId()));
        logObject.setRegistro("The user viewed the notifications");
        sendMessage(logObject);
    }

    public Integer countUser(){
        return listAllUsers().size();
    }
}

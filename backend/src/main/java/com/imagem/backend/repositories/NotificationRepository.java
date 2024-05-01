package com.imagem.backend.repositories;

import com.imagem.backend.domain.Notification;
import com.imagem.backend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {

    List<Notification> findByUserAndTipoNotificacao(User user, String tipoNotificacao);

    List<Notification> findByTipoNotificacao(String tipoNotificacao);

}

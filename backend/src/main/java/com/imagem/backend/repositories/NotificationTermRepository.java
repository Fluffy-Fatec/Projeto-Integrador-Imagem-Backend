package com.imagem.backend.repositories;

import com.imagem.backend.domain.NotificationTerm;
import com.imagem.backend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationTermRepository extends JpaRepository<NotificationTerm, Integer> {

    List<NotificationTerm> findByUser(User user);
}

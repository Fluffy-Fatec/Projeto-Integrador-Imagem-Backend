package com.imagem.backend.repositories;

import com.imagem.backend.domain.Invite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InviteRepository extends JpaRepository<Invite, Integer> {

    Invite findBytokeninvite(String tokenInvite);
}

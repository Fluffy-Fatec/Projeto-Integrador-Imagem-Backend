package com.imagem.backend.repositories;

import com.imagem.backend.domain.StatusTerm;
import com.imagem.backend.domain.Term;
import com.imagem.backend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusTermoRepository extends JpaRepository<StatusTerm, Integer> {

    StatusTerm findByTermoAndUser(Term termo, User user);

}

package com.imagem.backend.repositories;

import com.imagem.backend.domain.StatusTerm;
import com.imagem.backend.domain.Term;
import com.imagem.backend.domain.TermFunction;
import com.imagem.backend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StatusTermoRepository extends JpaRepository<StatusTerm, Integer> {

    List<StatusTerm> findByTermoAndUser(Term termo, User user);

    StatusTerm findByTermoAndUserAndTermoFuncao(Term termo, User user, TermFunction termFunction);

}

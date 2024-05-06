package com.imagem.backend.repositories;

import com.imagem.backend.domain.Term;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TermRepository extends JpaRepository<Term, Integer> {

    Term findByAtualVersao(boolean a);
}

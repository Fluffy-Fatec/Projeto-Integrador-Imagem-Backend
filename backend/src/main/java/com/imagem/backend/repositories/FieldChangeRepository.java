package com.imagem.backend.repositories;

import com.imagem.backend.domain.FieldChange;
import com.imagem.backend.domain.Invite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FieldChangeRepository extends JpaRepository<FieldChange, Integer> {
}

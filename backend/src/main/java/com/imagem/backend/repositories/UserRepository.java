package com.imagem.backend.repositories;

import com.imagem.backend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, Integer> {

    UserDetails findByUsername(String username);

    Boolean existsByEmail(String email);

}

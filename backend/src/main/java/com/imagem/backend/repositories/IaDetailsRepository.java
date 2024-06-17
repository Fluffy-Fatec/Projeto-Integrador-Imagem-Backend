package com.imagem.backend.repositories;

import com.imagem.backend.domain.IaDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IaDetailsRepository extends JpaRepository<IaDetails, Integer> {
    IaDetails findTopByOrderByIaDatetimeDeployDesc();
}

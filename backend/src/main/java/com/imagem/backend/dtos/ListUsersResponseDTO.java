package com.imagem.backend.dtos;


import com.imagem.backend.domain.ENUM.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListUsersResponseDTO {

    private Integer id;

    private String name;

    private String email;

    private Timestamp creation_date;

    private UserRole userRole;
}

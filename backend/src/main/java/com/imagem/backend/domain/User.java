package com.imagem.backend.domain;


import com.imagem.backend.domain.ENUM.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "app_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 255, nullable = true)
    private String username;

    @Column(length = 255, nullable = true)
    private String password;

    @Column(length = 255, nullable = true)
    private UserRole role;

    @Column(length = 255, nullable = true)
    private String nome;

    @Column(length = 255, nullable = true)
    private String email;

    @Column(length = 255, nullable = true)
    private String celular;

    @Column(length = 255, nullable = true)
    private String cpf;

    @Column(nullable = true, insertable = false)
    private Timestamp creationdate;

    @OneToMany(mappedBy = "solicitante")
    private List<Invite> invites;

    @OneToMany(mappedBy = "user")
    private List<FieldChange> userField;

    @OneToMany(mappedBy = "admin")
    private List<FieldChange> adminField;

    @OneToMany(mappedBy = "user")
    private List<StatusTerm> statusTerm;

    @OneToMany(mappedBy = "user")
    private List<Notification> notifications;

    @OneToMany(mappedBy = "user")
    private List<NotificationTerm> notificationTerms;

    public User(String login, String password, UserRole role){
        this.username = login;
        this.password = password;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == UserRole.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"),
                new SimpleGrantedAuthority("ROLE_USER"));

        else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}


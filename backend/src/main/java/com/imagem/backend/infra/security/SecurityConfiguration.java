package com.imagem.backend.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.GET, "/term").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/register/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/register/adm").permitAll()
                        .requestMatchers(HttpMethod.GET, "/v3/api-docs.yaml").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/user/term").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/invite").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/auth/update/pass").hasRole("USER")
                        .requestMatchers(HttpMethod.PUT, "/auth/update/user").hasRole("USER")
                        .requestMatchers(HttpMethod.PUT, "/auth/update/user/approve").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/auth/update/user/role").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/auth/update/user/list").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/auth/delete/user/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/auth/list/user").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/auth/list/user/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/auth/user/logged").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/auth/delete/user/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/graphics/list").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/graphics/countries").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/graphics/datasource").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/graphics/datasource/list/**").hasRole("USER")
                        .requestMatchers(HttpMethod.DELETE, "/graphics/review/**").hasRole("USER")
                        .requestMatchers(HttpMethod.PUT, "/graphics/update/**").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/graphics/listByDateRange").permitAll()
                        .requestMatchers(HttpMethod.GET, "/graphics/review/report").permitAll()
                        .requestMatchers(HttpMethod.GET, "/term/function/list").permitAll()
                        .requestMatchers(HttpMethod.GET, "/term/function/accept").permitAll()
                        .requestMatchers(HttpMethod.GET, "/graphics/word").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/auth/field/notification").hasRole("USER")
                        .requestMatchers(HttpMethod.PUT, "/auth/notification/update/**").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/term/notification").hasRole("USER")
                        .requestMatchers(HttpMethod.PUT, "/term/notification/update/**").hasRole("USER")
                        .anyRequest()
                        .authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
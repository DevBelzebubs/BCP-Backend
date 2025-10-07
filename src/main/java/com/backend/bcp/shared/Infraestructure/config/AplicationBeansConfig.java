package com.backend.bcp.shared.Infraestructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.backend.bcp.shared.Aplication.Security.persistence.AuthPersistence;
import com.backend.bcp.shared.Aplication.Security.ports.in.AuthService;
import com.backend.bcp.shared.Aplication.Security.ports.out.TokenService;
import com.backend.bcp.shared.Aplication.Security.ports.out.UserRepository;

@Configuration
public class AplicationBeansConfig {
    @Bean
    public AuthService authService(UserRepository userRepository, TokenService tokenService) {
        return new AuthPersistence(userRepository, tokenService);
    }
}

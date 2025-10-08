package com.backend.bcp.shared.Infraestructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.backend.bcp.shared.Aplication.Security.persistence.AuthPersistence;
import com.backend.bcp.shared.Aplication.Security.ports.in.AuthService;
import com.backend.bcp.shared.Aplication.Security.ports.out.TokenService;
import com.backend.bcp.shared.Aplication.Security.ports.out.UserRepository;
import com.backend.bcp.shared.Infraestructure.repo.SpringADataBackofficeRepository;
import com.backend.bcp.shared.Infraestructure.repo.SpringDataAsesorRepository;
import com.backend.bcp.shared.Infraestructure.repo.SpringDataClientRepository;
import com.backend.bcp.shared.Infraestructure.repo.SpringDataEmpleadoRepository;

@Configuration
public class AplicationBeansConfig {
    @Bean
    public AuthService authService(UserRepository userRepository,SpringDataClientRepository clientRepository,
    SpringDataEmpleadoRepository empleadoRepository,SpringDataAsesorRepository asesorRepository,SpringADataBackofficeRepository backofficeRepository ,TokenService tokenService) {
        return new AuthPersistence(userRepository,clientRepository,empleadoRepository,asesorRepository,backofficeRepository,tokenService);
    }
}

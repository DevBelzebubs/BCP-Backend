package com.backend.bcp.shared.Infraestructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.backend.bcp.app.Usuario.Infraestructure.repo.Cliente.SpringDataClientRepository;
import com.backend.bcp.app.Usuario.Infraestructure.repo.Empleado.SpringADataBackofficeRepository;
import com.backend.bcp.app.Usuario.Infraestructure.repo.Empleado.SpringDataAdminRepository;
import com.backend.bcp.app.Usuario.Infraestructure.repo.Empleado.SpringDataAsesorRepository;
import com.backend.bcp.app.Usuario.Infraestructure.repo.Empleado.SpringDataEmpleadoRepository;
import com.backend.bcp.shared.Aplication.Security.persistence.AuthPersistence;
import com.backend.bcp.shared.Aplication.Security.ports.in.AuthService;
import com.backend.bcp.shared.Aplication.Security.ports.out.TokenService;
import com.backend.bcp.shared.Aplication.Security.ports.out.UserRepository;

@Configuration
public class AplicationBeansConfig {
    @Bean
    public AuthService authService(UserRepository userRepository,SpringDataClientRepository clientRepository,
    SpringDataEmpleadoRepository empleadoRepository,SpringDataAsesorRepository asesorRepository,SpringADataBackofficeRepository backofficeRepository,SpringDataAdminRepository adminRepository, TokenService tokenService) {
        return new AuthPersistence(userRepository,clientRepository,empleadoRepository,asesorRepository,backofficeRepository,adminRepository,tokenService);
    }
}

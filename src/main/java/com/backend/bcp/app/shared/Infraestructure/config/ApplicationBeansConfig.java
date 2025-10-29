package com.backend.bcp.app.Shared.Infraestructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.backend.bcp.app.Prestamo.Application.mapper.PrestamoMapper;
import com.backend.bcp.app.Shared.Application.Security.persistence.AuthPersistence;
import com.backend.bcp.app.Shared.Application.Security.ports.in.AuthService;
import com.backend.bcp.app.Shared.Application.Security.ports.out.TokenService;
import com.backend.bcp.app.Shared.Application.Security.ports.out.UserRepository;
import com.backend.bcp.app.Usuario.Application.mapper.JasperMapper;
import com.backend.bcp.app.Usuario.Application.persistence.ReporteDiarioService;
import com.backend.bcp.app.Usuario.Application.ports.in.Empleado.GenerarReporteDiarioUseCase;
import com.backend.bcp.app.Usuario.Application.ports.out.Empleado.GeneradorReporteDiarioPdf;
import com.backend.bcp.app.Usuario.Application.ports.out.Empleado.ReporteDiarioDataPort;
import com.backend.bcp.app.Usuario.Infraestructure.repo.Cliente.SpringDataClientRepository;
import com.backend.bcp.app.Usuario.Infraestructure.repo.Empleado.SpringADataBackofficeRepository;
import com.backend.bcp.app.Usuario.Infraestructure.repo.Empleado.SpringDataAdminRepository;
import com.backend.bcp.app.Usuario.Infraestructure.repo.Empleado.SpringDataAsesorRepository;
import com.backend.bcp.app.Usuario.Infraestructure.repo.Empleado.SpringDataEmpleadoRepository;

@Configuration
public class ApplicationBeansConfig {
    @Bean
    public AuthService authService(UserRepository userRepository,SpringDataClientRepository clientRepository,
    SpringDataEmpleadoRepository empleadoRepository,SpringDataAsesorRepository asesorRepository,SpringADataBackofficeRepository backofficeRepository,SpringDataAdminRepository adminRepository, TokenService tokenService,PasswordEncoder passwordEncoder) {
        return new AuthPersistence(userRepository,clientRepository,empleadoRepository,asesorRepository,backofficeRepository,adminRepository,tokenService,passwordEncoder);
    }
    @Bean
    public GenerarReporteDiarioUseCase generarReporteDiarioUseCase(
            ReporteDiarioDataPort reporteDiarioDataPort,
            GeneradorReporteDiarioPdf generadorReporteDiarioPdf,
            PrestamoMapper prestamoMapper,
            JasperMapper jasperMapper
            ) {
        return new ReporteDiarioService(reporteDiarioDataPort, generadorReporteDiarioPdf, prestamoMapper,jasperMapper);
    }
}
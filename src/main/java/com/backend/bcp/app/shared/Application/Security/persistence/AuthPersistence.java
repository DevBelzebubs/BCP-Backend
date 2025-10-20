package com.backend.bcp.app.shared.Application.Security.persistence;


import java.util.concurrent.atomic.AtomicReference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.backend.bcp.app.Usuario.Infraestructure.repo.Cliente.SpringDataClientRepository;
import com.backend.bcp.app.Usuario.Infraestructure.repo.Empleado.SpringADataBackofficeRepository;
import com.backend.bcp.app.Usuario.Infraestructure.repo.Empleado.SpringDataAdminRepository;
import com.backend.bcp.app.Usuario.Infraestructure.repo.Empleado.SpringDataAsesorRepository;
import com.backend.bcp.app.Usuario.Infraestructure.repo.Empleado.SpringDataEmpleadoRepository;
import com.backend.bcp.app.shared.Application.Security.dto.in.UsuarioDTO;
import com.backend.bcp.app.shared.Application.Security.dto.out.LoginResponseDTO;
import com.backend.bcp.app.shared.Application.Security.ports.in.AuthService;
import com.backend.bcp.app.shared.Application.Security.ports.out.TokenService;
import com.backend.bcp.app.shared.Application.Security.ports.out.UserRepository;
import com.backend.bcp.app.shared.Domain.Usuario;

public class AuthPersistence implements AuthService{
    @Autowired
    private PasswordEncoder passwordEncoder;
    private final UserRepository usuarioRepository;
    private final SpringDataClientRepository clienteRepository;
    private final SpringDataEmpleadoRepository empleadoRepository;
    private final SpringDataAsesorRepository asesorRepository;
    private final SpringADataBackofficeRepository backOfficeRepository;
    private final SpringDataAdminRepository adminRepository;
    private final TokenService tokenService;

    public AuthPersistence(UserRepository usuarioRepository, SpringDataClientRepository clienteRepository,
            SpringDataEmpleadoRepository empleadoRepository, SpringDataAsesorRepository asesorRepository,
            SpringADataBackofficeRepository backOfficeRepository,SpringDataAdminRepository adminRepository, TokenService tokenService) {
        this.usuarioRepository = usuarioRepository;
        this.clienteRepository = clienteRepository;
        this.empleadoRepository = empleadoRepository;
        this.asesorRepository = asesorRepository;
        this.backOfficeRepository = backOfficeRepository;
        this.adminRepository = adminRepository;
        this.tokenService = tokenService;
    }

    @Override
    public LoginResponseDTO login(String nombre, String contrasena) {
        UsuarioDTO userDto = usuarioRepository.findByNombre(nombre).orElseThrow(() -> 
            new UsernameNotFoundException("Usuario no encontrado") 
        );
        if (!passwordEncoder.matches(contrasena, userDto.contrasena())) {
            throw new BadCredentialsException("Contraseña incorrecta");
        }
        AtomicReference<String> tipoUsuario = new AtomicReference<>("DESCONOCIDO");
        if (clienteRepository.findByIdUsuario_Id(userDto.id()).isPresent()) {
            tipoUsuario.set("CLIENTE");
        } else {
        empleadoRepository.findByIdUsuario_Id(userDto.id()).ifPresent(empleado -> {
        if (empleado.getIdEmpleado() != null) {
            if (asesorRepository.findByIdEmpleado_IdEmpleado(empleado.getIdEmpleado()).isPresent()) {
                tipoUsuario.set("ASESOR");
            } else if (backOfficeRepository.findByIdEmpleado_IdEmpleado(empleado.getIdEmpleado()).isPresent()) {
                tipoUsuario.set("BACKOFFICE");
            }else if (adminRepository.findByIdEmpleado_IdEmpleado(empleado.getIdEmpleado()).isPresent()){
                tipoUsuario.set("ADMIN");
            } 
            else {
                tipoUsuario.set("EMPLEADO");
            }
        }
        });
        }
        Usuario domainUser = new Usuario(
            userDto.id(), 
            userDto.nombre(), 
            userDto.contrasena(),
            userDto.correo(), 
            userDto.dni(), 
            userDto.direccion(), 
            userDto.telefono()
        );
        String token = tokenService.generateToken(domainUser, tipoUsuario.get());
        return new LoginResponseDTO(userDto.nombre(),token,tipoUsuario.get());
    }
}

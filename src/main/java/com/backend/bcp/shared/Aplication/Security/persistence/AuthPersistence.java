package com.backend.bcp.shared.Aplication.Security.persistence;


import java.util.concurrent.atomic.AtomicReference;

import com.backend.bcp.app.Usuario.Infraestructure.repo.Cliente.SpringDataClientRepository;
import com.backend.bcp.app.Usuario.Infraestructure.repo.Empleado.SpringADataBackofficeRepository;
import com.backend.bcp.app.Usuario.Infraestructure.repo.Empleado.SpringDataAdminRepository;
import com.backend.bcp.app.Usuario.Infraestructure.repo.Empleado.SpringDataAsesorRepository;
import com.backend.bcp.app.Usuario.Infraestructure.repo.Empleado.SpringDataEmpleadoRepository;
import com.backend.bcp.shared.Aplication.Security.dto.out.LoginResponseDTO;
import com.backend.bcp.shared.Aplication.Security.ports.in.AuthService;
import com.backend.bcp.shared.Aplication.Security.ports.out.TokenService;
import com.backend.bcp.shared.Aplication.Security.ports.out.UserRepository;
import com.backend.bcp.shared.Domain.Usuario;

public class AuthPersistence implements AuthService{
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
        Usuario user = usuarioRepository.findByNombre(nombre).orElseThrow(() -> new RuntimeException("User not found"));
        if (!user.getContrasena().equals(contrasena)) {
            throw new RuntimeException("Contraseña incorrecta");
        }
        AtomicReference<String> tipoUsuario = new AtomicReference<>("DESCONOCIDO");
        if (clienteRepository.findByIdUsuario_Id(user.getId()).isPresent()) {
            tipoUsuario.set("CLIENTE");
        } else {
        empleadoRepository.findByIdUsuario_Id(user.getId()).ifPresent(empleado -> {
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
        String token = tokenService.generateToken(user, tipoUsuario.get());
        return new LoginResponseDTO(user.getNombre(),token,tipoUsuario.get());
    }
}

package com.backend.bcp.shared.Aplication.Security.persistence;

import com.backend.bcp.shared.Aplication.Security.dto.out.LoginResponseDTO;
import com.backend.bcp.shared.Aplication.Security.ports.in.AuthService;
import com.backend.bcp.shared.Aplication.Security.ports.out.TokenService;
import com.backend.bcp.shared.Aplication.Security.ports.out.UserRepository;
import com.backend.bcp.shared.Domain.Usuario;

public class AuthPersistence implements AuthService{
    private final UserRepository usuarioRepository;
    private final TokenService tokenService;
    public AuthPersistence(UserRepository usuarioRepository, TokenService tokenService) {
        this.usuarioRepository = usuarioRepository;
        this.tokenService = tokenService;
    }
    @Override
    public LoginResponseDTO login(String nombre, String contrasena) {
        Usuario user = usuarioRepository.findByNombre(nombre).orElseThrow(() -> new RuntimeException("User not found"));
        if (!user.getContrasena().equals(contrasena)) {
            throw new RuntimeException("Contraseña incorrecta");
        }
        String token = tokenService.generateToken(user);
        return new LoginResponseDTO(user.getNombre(),token);
    }
}

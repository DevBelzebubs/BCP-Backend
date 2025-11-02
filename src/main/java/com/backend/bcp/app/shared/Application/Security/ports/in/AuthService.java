package com.backend.bcp.app.Shared.Application.Security.ports.in;

import com.backend.bcp.app.Shared.Application.Security.dto.out.LoginResponseDTO;

public interface AuthService {
    LoginResponseDTO login(String username, String password);
    LoginResponseDTO generarTokenServicio(String serviceName, String serviceRole);
}

package com.backend.bcp.app.shared.Application.Security.ports.in;

import com.backend.bcp.app.shared.Application.Security.dto.out.LoginResponseDTO;

public interface AuthService {
    LoginResponseDTO login(String username, String password);
}

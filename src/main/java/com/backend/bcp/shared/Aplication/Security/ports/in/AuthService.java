package com.backend.bcp.shared.Aplication.Security.ports.in;

import com.backend.bcp.shared.Aplication.Security.dto.out.LoginResponseDTO;

public interface AuthService {
    LoginResponseDTO login(String username, String password);
}

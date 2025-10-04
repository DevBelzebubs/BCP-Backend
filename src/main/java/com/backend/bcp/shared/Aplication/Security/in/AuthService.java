package com.backend.bcp.shared.Aplication.Security.in;

import com.backend.bcp.shared.Infraestructure.dto.LoginDTO;

public interface AuthService {
    LoginDTO login(String username, String password);
    
}

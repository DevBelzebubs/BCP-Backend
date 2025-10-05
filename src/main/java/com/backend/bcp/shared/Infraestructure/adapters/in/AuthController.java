package com.backend.bcp.shared.Infraestructure.adapters.in;

import org.springframework.web.bind.annotation.*;

import com.backend.bcp.shared.Aplication.Security.dto.in.LoginDTO;
import com.backend.bcp.shared.Aplication.Security.dto.out.LoginResponseDTO;
import com.backend.bcp.shared.Aplication.Security.ports.in.AuthService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/auth")

public class AuthController {
    private AuthService authService;
    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginDTO loginDTO) {
        return authService.login(loginDTO.username(), loginDTO.password());
    }
}

package com.backend.bcp.shared.Infraestructure.adapters.in;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.backend.bcp.shared.Aplication.Security.in.AuthService;
import com.backend.bcp.shared.Infraestructure.dto.LoginDTO;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/auth")

public class AuthController {
    @Autowired
    private AuthService authService;
    @PostMapping("/login")
    public LoginDTO login(@RequestBody LoginDTO loginDTO) {
        return authService.login(loginDTO.getUsername(), loginDTO.getPassword());
    }
}

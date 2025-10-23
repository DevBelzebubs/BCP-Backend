package com.backend.bcp.app.Shared.Infraestructure.adapters.in;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;

import com.backend.bcp.app.Shared.Application.Security.dto.in.LoginDTO;
import com.backend.bcp.app.Shared.Application.Security.dto.out.LoginResponseDTO;
import com.backend.bcp.app.Shared.Application.Security.ports.in.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private AuthService authService;
    
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) { 
        try {
            LoginResponseDTO response = authService.login(loginDTO.getNombre(), loginDTO.getContrasena());
            return ResponseEntity.ok(response);
        }
        catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        }
        catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", e.getMessage()));
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error interno del servidor"));
        }
    }
}

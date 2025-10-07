package com.backend.bcp.shared.Infraestructure.adapters.in;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import com.backend.bcp.shared.Aplication.Security.dto.in.LoginDTO;
import com.backend.bcp.shared.Aplication.Security.dto.out.LoginResponseDTO;
import com.backend.bcp.shared.Aplication.Security.ports.in.AuthService;

import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private AuthService authService;
    
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        System.out.println("=== Petición recibida ===");
        System.out.println("Objeto LoginDTO: " + loginDTO);
        System.out.println("Nombre: " + loginDTO.getNombre());
        System.out.println("Contraseña: " + loginDTO.getContrasena());  
        try {
            System.out.println(">>> Nombre recibido: " + loginDTO.getNombre());
            System.out.println(">>> Contraseña recibida: " + loginDTO.getContrasena());
            LoginResponseDTO response = authService.login(loginDTO.getNombre(), loginDTO.getContrasena());
            return ResponseEntity.ok(response);
        } 
        catch (UsernameNotFoundException e) {
            System.out.println(">>> Nombre recibido: " + loginDTO.getNombre());
            System.out.println(">>> Contraseña recibida: " + loginDTO.getContrasena());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        } 
        catch (BadCredentialsException e) {
            System.out.println(">>> Nombre recibido: " + loginDTO.getNombre());
            System.out.println(">>> Contraseña recibida: " + loginDTO.getContrasena());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", e.getMessage()));
        } 
        catch (Exception e) {
            e.printStackTrace();
            System.out.println(">>> Nombre recibido: " + loginDTO.getNombre());
            System.out.println(">>> Contraseña recibida: " + loginDTO.getContrasena());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error interno del servidor"));
        }
    }
}

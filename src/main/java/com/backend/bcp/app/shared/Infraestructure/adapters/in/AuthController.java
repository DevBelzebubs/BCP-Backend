package com.backend.bcp.app.shared.Infraestructure.adapters.in;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
//WORKS!
import org.springframework.web.bind.annotation.*;

import com.backend.bcp.app.shared.Application.Security.dto.in.LoginDTO;
import com.backend.bcp.app.shared.Application.Security.dto.out.LoginResponseDTO;
import com.backend.bcp.app.shared.Application.Security.ports.in.AuthService;
import com.backend.bcp.app.shared.Infraestructure.config.ApiResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDTO>> login(@RequestBody LoginDTO loginDTO) { 
        try {
            LoginResponseDTO response = authService.login(loginDTO.getNombre(), loginDTO.getContrasena());
            return ResponseEntity.ok(ApiResponse.success("Login exitoso", response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.error(e.getMessage(), null));
        }
    }
    @GetMapping(value = "/generar-token-servicio", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<LoginResponseDTO>> generarTokenServicio() {
        try {
            LoginResponseDTO responseDTO = authService.generarTokenServicio("payflow-service-account", "PAYFLOW_SERVICE");
            return ResponseEntity.ok(ApiResponse.success("Token generado correctamente", responseDTO));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error interno del servidor", null));
        }
    }
    
}

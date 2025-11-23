package com.backend.bcp.app.shared.Infraestructure.adapters.in;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.bcp.app.shared.Application.Security.dto.in.UsuarioDTO;
import com.backend.bcp.app.shared.Application.Security.ports.out.UserRepository;
import com.backend.bcp.app.shared.Application.dto.in.LoadClientDataDTO;
import com.backend.bcp.app.shared.Domain.ports.in.GestionClienteUseCase;
import com.backend.bcp.app.shared.Infraestructure.config.ApiResponse;

@RestController
@RequestMapping("/api/cliente")
public class DataController {
    private final GestionClienteUseCase gestionClienteUseCase;
    private final UserRepository userRepository;

    public DataController(GestionClienteUseCase gestionClienteUseCase, UserRepository userRepository) {
        this.gestionClienteUseCase = gestionClienteUseCase;
        this.userRepository = userRepository;
    }
    @GetMapping("/dashboard")
    public ResponseEntity<ApiResponse<LoadClientDataDTO>> getDashboardData() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            UsuarioDTO usuario = userRepository.findByNombre(username)
                    .orElseThrow(()-> new RuntimeException("Usuario autenticado no encontrado"));
            
            LoadClientDataDTO dashboardData = gestionClienteUseCase.cargarDatosDashboard(usuario.dni());

            return ResponseEntity.ok(ApiResponse.success("Datos del dashboard cargados", dashboardData));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage(), null));
        }
    }
}

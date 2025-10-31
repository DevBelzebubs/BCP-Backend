package com.backend.bcp.app.Shared.Infraestructure.adapters.in;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.bcp.app.Shared.Application.Security.dto.in.UsuarioDTO;
import com.backend.bcp.app.Shared.Application.Security.ports.out.UserRepository;
import com.backend.bcp.app.Shared.Application.dto.in.LoadClientDataDTO;
import com.backend.bcp.app.Shared.Domain.ports.in.GestionClienteUseCase;

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
    public ResponseEntity<?> getDashboardData() {
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            UsuarioDTO usuario = userRepository.findByNombre(username).orElseThrow(()-> new RuntimeException("Usuario autenticado no encontrado"));
            LoadClientDataDTO dashboardData = gestionClienteUseCase.cargarDatosDashboard(usuario.dni());

            return ResponseEntity.ok(dashboardData);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}

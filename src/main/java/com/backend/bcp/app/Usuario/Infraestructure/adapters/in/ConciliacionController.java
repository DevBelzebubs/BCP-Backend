package com.backend.bcp.app.Usuario.Infraestructure.adapters.in;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;

import com.backend.bcp.app.Shared.Application.Security.ports.out.UserRepository;
import com.backend.bcp.app.Usuario.Application.dto.in.ConciliacionRequestDTO;
import com.backend.bcp.app.Usuario.Application.dto.in.ConciliacionResultDTO;
import com.backend.bcp.app.Usuario.Application.ports.in.Empleado.GestionarConciliacionUseCase;

import jakarta.validation.Valid;
//WORKS!
@RestController
@RequestMapping("/api/conciliacion")
public class ConciliacionController {
    private final GestionarConciliacionUseCase gestionarConciliacionUseCase;
    private final UserRepository userRepository;
    public ConciliacionController(GestionarConciliacionUseCase gestionarConciliacionUseCase,
            UserRepository userRepository) {
        this.gestionarConciliacionUseCase = gestionarConciliacionUseCase;
        this.userRepository = userRepository;
    }
    @PostMapping("/procesar")
    public ResponseEntity<?> procesarConciliacion(@Valid @RequestBody ConciliacionRequestDTO request) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            
            Long backOfficeUserId = userRepository.findByNombre(username)
                    .map(dto -> dto.id()) 
                    .orElseThrow(() -> new RuntimeException("Usuario autenticado no encontrado en la base de datos"));

            ConciliacionResultDTO resultado = gestionarConciliacionUseCase.procesarConciliacion(request, backOfficeUserId);
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(Map.of("error", "Error procesando la conciliación: " + e.getMessage()));
        }
    }
}

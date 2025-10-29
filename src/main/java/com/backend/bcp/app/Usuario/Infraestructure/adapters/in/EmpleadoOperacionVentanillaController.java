package com.backend.bcp.app.Usuario.Infraestructure.adapters.in;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.bcp.app.Comprobante.Application.dto.ComprobanteDTO;
import com.backend.bcp.app.Shared.Application.Security.ports.out.UserRepository;
import com.backend.bcp.app.Usuario.Application.dto.in.Empleado.DepositoVentanillaDTO;
import com.backend.bcp.app.Usuario.Application.dto.in.Empleado.PagoVentanillaDTO;
import com.backend.bcp.app.Usuario.Application.dto.in.Empleado.RetiroVentanillaDTO;
import com.backend.bcp.app.Usuario.Application.ports.in.Empleado.GestionOperacionVentanillaUseCase;

import jakarta.validation.Valid;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/empleado/operaciones-ventanilla")
@PreAuthorize("hasAnyRole('EMPLEADO', 'ASESOR', 'BACKOFFICE', 'ADMIN')")
public class EmpleadoOperacionVentanillaController {
    private final GestionOperacionVentanillaUseCase gestionOperacionVentanillaUseCase;
    private final UserRepository userRepository;
    public EmpleadoOperacionVentanillaController(GestionOperacionVentanillaUseCase gestionOperacionVentanillaUseCase,
            UserRepository userRepository) {
        this.gestionOperacionVentanillaUseCase = gestionOperacionVentanillaUseCase;
        this.userRepository = userRepository;
    }
    @PostMapping("/deposito")
    public ResponseEntity<?> registrarDeposito(@Valid @RequestBody DepositoVentanillaDTO request) {
        try{
            Long empleadoId = getAuthenticatedEmpleadoUsuarioId();
            ComprobanteDTO comprobante = gestionOperacionVentanillaUseCase.registrarDepositoVentanilla(request, empleadoId);
            return ResponseEntity.ok(comprobante);
        }catch(RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch(Exception e){
            return ResponseEntity.status(500).body("Error interno del servidor");
        }
    }
    @PostMapping("/retiro")
    public ResponseEntity<?> registrarRetiro(@Valid @RequestBody RetiroVentanillaDTO request) {
         try {
            Long empleadoId = getAuthenticatedEmpleadoUsuarioId();
            ComprobanteDTO comprobante = gestionOperacionVentanillaUseCase.registrarRetiroVentanilla(request, empleadoId);
            return ResponseEntity.status(HttpStatus.CREATED).body(comprobante);
        } catch (IllegalStateException | IllegalArgumentException e) {
             return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Error interno al procesar el retiro."));
        }
    }
    @PostMapping("/pago")
    public ResponseEntity<?> registrarPago(@Valid @RequestBody PagoVentanillaDTO request) {
         try {
            Long empleadoId = getAuthenticatedEmpleadoUsuarioId();
            ComprobanteDTO comprobante = gestionOperacionVentanillaUseCase.registrarPagoVentanilla(request, empleadoId);
            return ResponseEntity.status(HttpStatus.CREATED).body(comprobante);
        } catch (IllegalStateException | IllegalArgumentException e) {
             return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Error interno al procesar el pago."));
        }
    }
    private Long getAuthenticatedEmpleadoUsuarioId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByNombre(username)
                .map(dto -> dto.id())
                .orElseThrow(() -> new RuntimeException("Empleado autenticado no encontrado en BD"));
    }
    
}

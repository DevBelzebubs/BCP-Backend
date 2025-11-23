package com.backend.bcp.app.shared.Infraestructure.adapters.in;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.bcp.app.Usuario.Application.ports.in.Admin.GestionUsuariosAdminUseCase;
import com.backend.bcp.app.shared.Application.Security.ports.out.UserRepository;
import com.backend.bcp.app.shared.Application.dto.in.CambiarRolRequestDTO;
import com.backend.bcp.app.shared.Application.dto.out.EmpleadoRolDTO;
import com.backend.bcp.app.shared.Infraestructure.config.ApiResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin/usuarios")
public class AdminUsuariosController {
    private final GestionUsuariosAdminUseCase gestionUsuariosUseCase;
    private final UserRepository userRepository;

    public AdminUsuariosController(GestionUsuariosAdminUseCase gestionUsuariosUseCase, UserRepository userRepository) {
        this.gestionUsuariosUseCase = gestionUsuariosUseCase;
        this.userRepository = userRepository;
    }
    @GetMapping("/roles")
    public ResponseEntity<ApiResponse<List<EmpleadoRolDTO>>> listarEmpleados() {
        List<EmpleadoRolDTO> empleados = gestionUsuariosUseCase.listarEmpleadosConRoles();
        return ResponseEntity.ok(ApiResponse.success("Lista de empleados y roles obtenida", empleados));
    }
    @PutMapping("/{empleadoId}/rol")
    public ResponseEntity<ApiResponse<EmpleadoRolDTO>> cambiarRol(@PathVariable Long empleadoId, @Valid @RequestBody CambiarRolRequestDTO request) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String adminUsername = authentication.getName();
            Long adminUserId = userRepository.findByNombre(adminUsername)
                    .map(dto -> dto.id())
                    .orElseThrow(() -> new RuntimeException("Admin no encontrado"));

            EmpleadoRolDTO empleadoActualizado = gestionUsuariosUseCase.cambiarRolEmpleado(empleadoId, request, adminUserId);
            return ResponseEntity.ok(ApiResponse.success("Rol actualizado exitosamente", empleadoActualizado));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage(), null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.error("Error interno al cambiar el rol.", null));
        }
    }
}

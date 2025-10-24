package com.backend.bcp.app.Usuario.Infraestructure.adapters;

import org.springframework.http.HttpHeaders;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.backend.bcp.app.Shared.Application.Security.ports.out.UserRepository;
import com.backend.bcp.app.Usuario.Application.dto.in.MarcarSospechosoRequestDTO;
import com.backend.bcp.app.Usuario.Application.dto.out.OperacionSupervisionDTO;
import com.backend.bcp.app.Usuario.Application.ports.in.Empleado.GestionSupervisionUseCase;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/backoffice/supervision")
public class SupervisionController {
    private final GestionSupervisionUseCase gestionSupervisionUseCase;
    private final UserRepository userRepository;

    public SupervisionController(GestionSupervisionUseCase gestionSupervisionUseCase, UserRepository userRepository) {
        this.gestionSupervisionUseCase = gestionSupervisionUseCase;
        this.userRepository = userRepository;
    }
    @GetMapping("/operaciones")
    public ResponseEntity<List<OperacionSupervisionDTO>> listarOperaciones() {
        try {
            List<OperacionSupervisionDTO> operaciones = gestionSupervisionUseCase.obtenerOperacionesParaSupervisar();
            return ResponseEntity.ok(operaciones);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
    @PostMapping("/marcar-sospechosa")
    public ResponseEntity<?> marcarComoSospechosa(@Valid @RequestBody MarcarSospechosoRequestDTO request) {
         try {
             Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
             String username = authentication.getName();
             Long backOfficeUserId = userRepository.findByNombre(username)
                     .map(dto -> dto.id())
                     .orElseThrow(() -> new RuntimeException("Usuario autenticado no encontrado"));

             Long alertaId = gestionSupervisionUseCase.marcarOperacionComoSospechosa(request, backOfficeUserId);
             return ResponseEntity.ok(Map.of(
                     "mensaje", "Operación marcada como sospechosa. Alerta generada.",
                     "alertaId", alertaId
             ));
         } catch (Exception e) {
             e.printStackTrace();
             return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
         }
    }
    @GetMapping(value = "/reporte-global", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generarReporteGlobal() {
        try {
            byte[] pdfBytes = gestionSupervisionUseCase.generarReporteGlobalOperaciones();

            String filename = "Reporte_Global_Operaciones_" + LocalDate.now().toString() + ".pdf";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("inline", filename);
            headers.setContentLength(pdfBytes.length);

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

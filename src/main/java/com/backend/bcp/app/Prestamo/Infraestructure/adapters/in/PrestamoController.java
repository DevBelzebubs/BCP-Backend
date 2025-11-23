package com.backend.bcp.app.Prestamo.Infraestructure.adapters.in;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.bcp.app.Prestamo.Application.dto.in.RechazoRequestDTO;
import com.backend.bcp.app.Prestamo.Application.dto.in.SolicitudCreditoDTO;
import com.backend.bcp.app.Prestamo.Application.dto.out.PrestamoResponseDTO;
import com.backend.bcp.app.Prestamo.Application.ports.in.SolicitudCreditoUseCase;
import com.backend.bcp.app.shared.Infraestructure.config.ApiResponse;

import jakarta.validation.Valid;
//WOKRS
@RestController
@RequestMapping("/api/prestamos")
public class PrestamoController {
    private final SolicitudCreditoUseCase solicitudCreditoUseCase;

    public PrestamoController(SolicitudCreditoUseCase solicitudCreditoUseCase) {
        this.solicitudCreditoUseCase = solicitudCreditoUseCase;
    }

    @PostMapping("/solicitar")
    public ResponseEntity<ApiResponse<PrestamoResponseDTO>> solicitarCredito(@Valid @RequestBody SolicitudCreditoDTO solicitudDTO) {
        try {
            PrestamoResponseDTO nuevaSolicitud = solicitudCreditoUseCase.crearSolicitudCredito(solicitudDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Solicitud creada correctamente", nuevaSolicitud));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error(e.getMessage(), null));
        }
    }
    @GetMapping
    public ResponseEntity<ApiResponse<List<PrestamoResponseDTO>>> listarSolicitudes() {
        return ResponseEntity.ok(ApiResponse.success("Listado de solicitudes", solicitudCreditoUseCase.obtenerSolicitudes()));
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PrestamoResponseDTO>> obtenerSolicitudPorId(@PathVariable Long id) {
        PrestamoResponseDTO solicitud = solicitudCreditoUseCase.findSolicitudById(id);
        if (solicitud == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Solicitud no encontrada", null));
        }
        return ResponseEntity.ok(ApiResponse.success("Solicitud encontrada", solicitud));
    }
    @PostMapping("/{id}/aprobar")
    public ResponseEntity<ApiResponse<String>> aprobarSolicitud(@PathVariable Long id) {
        Long asesorId = null; 
        try {
            solicitudCreditoUseCase.aprobarSolicitud(id, asesorId);
            return ResponseEntity.ok(ApiResponse.success("Solicitud " + id + " APROBADA.", null));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(e.getMessage(), null));
        }
    }
    @PostMapping("/{id}/rechazar")
    public ResponseEntity<ApiResponse<String>> rechazarSolicitud(@PathVariable Long id, @RequestBody RechazoRequestDTO request) {
        Long asesorId = null;
        try {
            solicitudCreditoUseCase.rechazarSolicitud(id, asesorId, request.motivo());
            return ResponseEntity.ok(ApiResponse.success("Solicitud " + id + " RECHAZADA.", null));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(e.getMessage(), null));
        }
    }
    @PostMapping("/{id}/solicitar-documentacion")
    public ResponseEntity<ApiResponse<String>> solicitarDocumentacion(@PathVariable Long id) {
        Long asesorId = null;
        try {
            solicitudCreditoUseCase.marcarPendiente(id, asesorId);
            return ResponseEntity.ok(ApiResponse.success("Solicitud " + id + " marcada como PENDIENTE_DOCUMENTACION.", null));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(e.getMessage(), null));
        }
    }
}
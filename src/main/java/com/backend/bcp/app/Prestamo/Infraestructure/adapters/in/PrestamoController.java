package com.backend.bcp.app.Prestamo.Infraestructure.adapters.in;

import java.util.List;
import java.util.Map;

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
    public ResponseEntity<PrestamoResponseDTO> solicitarCredito(@Valid @RequestBody SolicitudCreditoDTO solicitudDTO) {
        try {
            PrestamoResponseDTO nuevaSolicitud = solicitudCreditoUseCase.crearSolicitudCredito(solicitudDTO);
            return new ResponseEntity<>(nuevaSolicitud, HttpStatus.CREATED);
        } catch (Exception e) {
            System.err.println("Error en solicitarCredito: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping
    public ResponseEntity<List<PrestamoResponseDTO>> listarSolicitudes() {
        return ResponseEntity.ok(solicitudCreditoUseCase.obtenerSolicitudes());
    }
    @GetMapping("/{id}")
    public ResponseEntity<PrestamoResponseDTO> obtenerSolicitudPorId(@PathVariable Long id) {
        PrestamoResponseDTO solicitud = solicitudCreditoUseCase.findSolicitudById(id);
        if (solicitud == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(solicitud);
    }
    @PostMapping("/{id}/aprobar")
    public ResponseEntity<?> aprobarSolicitud(@PathVariable Long id) {
        Long asesorId = null; 
        try {
            solicitudCreditoUseCase.aprobarSolicitud(id, asesorId);
            return ResponseEntity.ok(Map.of("mensaje", "Solicitud " + id + " APROBADA."));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }
    @PostMapping("/{id}/rechazar")
    public ResponseEntity<?> rechazarSolicitud(@PathVariable Long id, @RequestBody RechazoRequestDTO request) {
        Long asesorId = null;
        try {
            solicitudCreditoUseCase.rechazarSolicitud(id, asesorId, request.motivo());
            return ResponseEntity.ok(Map.of("mensaje", "Solicitud " + id + " RECHAZADA."));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }
    @PostMapping("/{id}/solicitar-documentacion")
    public ResponseEntity<?> solicitarDocumentacion(@PathVariable Long id) {
        Long asesorId = null;
        try {
            solicitudCreditoUseCase.marcarPendiente(id, asesorId);
            return ResponseEntity.ok(Map.of("mensaje", "Solicitud " + id + " marcada como PENDIENTE_DOCUMENTACION."));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }
}
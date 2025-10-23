package com.backend.bcp.app.Prestamo.Infraestructure.adapters.in;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.bcp.app.Prestamo.Application.dto.in.SolicitudCreditoDTO;
import com.backend.bcp.app.Prestamo.Application.dto.out.PrestamoResponseDTO;
import com.backend.bcp.app.Prestamo.Application.ports.in.SolicitudCreditoUseCase;

import jakarta.validation.Valid;

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
}
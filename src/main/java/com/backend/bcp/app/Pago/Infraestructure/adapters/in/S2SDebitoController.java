package com.backend.bcp.app.Pago.Infraestructure.adapters.in;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.bcp.app.Comprobante.Application.dto.ComprobanteDTO;
import com.backend.bcp.app.Pago.Application.dto.in.payflow.DebitoDirectoRequestDTO;
import com.backend.bcp.app.Pago.Application.ports.in.DebitoDirectoS2SUseCase;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/s2s/debito")
@PreAuthorize("hasRole('PAYFLOW_SERVICE')")
public class S2SDebitoController {
    private final DebitoDirectoS2SUseCase debitoDirectoS2SUseCase;

    @Autowired
    public S2SDebitoController(DebitoDirectoS2SUseCase debitoDirectoS2SUseCase) {
        this.debitoDirectoS2SUseCase = debitoDirectoS2SUseCase;
    }

    @PostMapping("/ejecutar")
    public ResponseEntity<?> ejecutarDebitoDirecto(@Valid @RequestBody DebitoDirectoRequestDTO request) {
        try{
            ComprobanteDTO comprobante = debitoDirectoS2SUseCase.ejecutarDebito(request);
            return ResponseEntity.ok(comprobante);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(Map.of("Error", e.getMessage()));
        }
    }
}

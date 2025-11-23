package com.backend.bcp.app.Pago.Infraestructure.adapters.in;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.bcp.app.Comprobante.Application.dto.ComprobanteDTO;
import com.backend.bcp.app.Pago.Application.dto.in.payflow.DebitoRequestDTO;
import com.backend.bcp.app.Pago.Application.ports.in.RealizarPagoUseCase;
import com.backend.bcp.app.shared.Infraestructure.config.ApiResponse;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/s2s/pagos")
@PreAuthorize("hasRole('PAYFLOW_SERVICE')")
public class S2SPagoController {
    private final RealizarPagoUseCase realizarPagoUseCase;

    public S2SPagoController(RealizarPagoUseCase realizarPagoUseCase) {
        this.realizarPagoUseCase = realizarPagoUseCase;
    }
    @PostMapping("/solicitar-debito")
    public ResponseEntity<ApiResponse<ComprobanteDTO>> solicitarDebito(@Valid @RequestBody DebitoRequestDTO request) {
        System.out.println("[S2S PAGO] ¡PETICIÓN RECIBIDA DESDE PAYFLOW! - ID Pago BCP: " + request.idPagoBCP());
        
        try {
            ComprobanteDTO comprobanteDTO = realizarPagoUseCase.realizarPagoExterno(request);
            return ResponseEntity.ok(ApiResponse.success("Débito procesado correctamente", comprobanteDTO));
        } catch (RuntimeException e) {
            System.err.println("[S2S PAGO] Error de Runtime: " + e.getMessage());
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage(), null));
        } catch (Exception e) {
            System.err.println("[S2S PAGO] Error General: " + e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.error("Error interno al procesar el débito", null));
        }
    }
    
}

package com.backend.bcp.app.Pago.Infraestructure.adapters.in;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.bcp.app.Comprobante.Application.dto.ComprobanteDTO;
import com.backend.bcp.app.Pago.Application.dto.in.payflow.DebitoRequestDTO;
import com.backend.bcp.app.Pago.Application.ports.in.RealizarPagoUseCase;

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
    public ResponseEntity<?> solicitarDebito(@Valid @RequestBody DebitoRequestDTO request) {
        System.out.println("-------------------------------------------");
        System.out.println("[S2S PAGO] ¡PETICIÓN RECIBIDA DESDE PAYFLOW!");
        System.out.println("[S2S PAGO] DNI Cliente: " + request.dniCliente());
        System.out.println("[S2S PAGO] Monto: " + request.monto());
        System.out.println("[S2S PAGO] ID Pago BCP: " + request.idPagoBCP());
        try{
            ComprobanteDTO comprobanteDTO = realizarPagoUseCase.realizarPagoExterno(request);
            System.out.println("[S2S PAGO] Lógica de pago exitosa. Devolviendo comprobante.");
            System.out.println("-------------------------------------------");
            return ResponseEntity.ok(comprobanteDTO);
        }catch(RuntimeException e){
            System.err.println("[S2S PAGO] Error de Runtime: " + e.getMessage());
            System.err.println("-------------------------------------------");
            return ResponseEntity.badRequest().body(Map.of("Error",e.getMessage()));
        }catch(Exception e){
            System.err.println("[S2S PAGO] Error General: " + e.getMessage());
            System.err.println("-------------------------------------------");
            return ResponseEntity.status(500).body(Map.of("Error","Error interno al procesar el débito."));
        }
    }
    
}

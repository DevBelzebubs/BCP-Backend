package com.backend.bcp.app.Pago.Infraestructure.adapters.in;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.bcp.app.Comprobante.Aplication.dto.ComprobanteDTO;
import com.backend.bcp.app.Pago.Aplication.dto.in.PagoPendienteDTO;
import com.backend.bcp.app.Pago.Aplication.dto.in.PagoRequestDTO;
import com.backend.bcp.app.Pago.Aplication.ports.in.RealizarPagoUseCase;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/pagos")
public class PagoController {
    private final RealizarPagoUseCase realizarPagoUseCase;
    public PagoController(RealizarPagoUseCase realizarPagoUseCase) {
        this.realizarPagoUseCase = realizarPagoUseCase;
    }
    @GetMapping("/pendientes/usuario/{usuarioId}")
    public ResponseEntity<List<PagoPendienteDTO>> listarPagosPendientes(@PathVariable Long usuarioId) {
        List<PagoPendienteDTO> pendientes = realizarPagoUseCase.listarPagosPendientes(usuarioId);
        return ResponseEntity.ok(pendientes);
    }
    @PostMapping("/realizar")
    public ResponseEntity<ComprobanteDTO> realizarPago(@RequestBody PagoRequestDTO request) {
        ComprobanteDTO comprobante = realizarPagoUseCase.realizarPago(
            request.cuentaId(), 
            request.itemPagoId(),
            request.monto()
        );
        return ResponseEntity.ok(comprobante);
    }
    
}

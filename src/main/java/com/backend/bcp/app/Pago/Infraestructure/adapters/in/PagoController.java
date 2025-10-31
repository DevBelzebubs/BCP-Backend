package com.backend.bcp.app.Pago.Infraestructure.adapters.in;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.bcp.app.Comprobante.Application.dto.ComprobanteDTO;
import com.backend.bcp.app.Pago.Application.dto.in.PagoPendienteDTO;
import com.backend.bcp.app.Pago.Application.dto.in.PagoRequestDTO;
import com.backend.bcp.app.Pago.Application.dto.in.payflow.DebitoRequestDTO;
import com.backend.bcp.app.Pago.Application.ports.in.RealizarPagoUseCase;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
//WORKS!
@RestController
@RequestMapping("/api/pagos")
public class PagoController {
    private final RealizarPagoUseCase realizarPagoUseCase;
    public PagoController(RealizarPagoUseCase realizarPagoUseCase) {
        this.realizarPagoUseCase = realizarPagoUseCase;
    }
    @GetMapping("/pendientes/usuario/{dni}")
    public ResponseEntity<?> listarPagosPendientes(@PathVariable String dni) {
        try {
            List<PagoPendienteDTO> pendientes = realizarPagoUseCase.listarPagosPendientes(dni);
            return ResponseEntity.ok(pendientes);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Error interno al listar pagos: " + e.getMessage()));
        }
    }
    @PostMapping("/realizar")
    public ResponseEntity<?> realizarPago(@RequestBody PagoRequestDTO request) {
        try {
            ComprobanteDTO comprobante = realizarPagoUseCase.realizarPago(
                request.cuentaId(),
                request.pagoId()
            );
            return ResponseEntity.ok(comprobante);
        } catch (RuntimeException e) {
            e.getStackTrace();
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            e.getStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Error interno al procesar el pago."));
        }
    }
    @PostMapping("/solicitar-debito")
    public ResponseEntity<?> solicitarCredito(@Valid @RequestBody DebitoRequestDTO request) {
        try{
            ComprobanteDTO comprobante = realizarPagoUseCase.realizarPagoExterno(request);
            return ResponseEntity.ok(comprobante);
        }catch(RuntimeException e){
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }catch(Exception e){
           return ResponseEntity.status(500).body(Map.of("error", "Error interno al procesar el débito."));
        }
    }
    
}

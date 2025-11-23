package com.backend.bcp.app.Pago.Infraestructure.adapters.in;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.bcp.app.Comprobante.Application.dto.ComprobanteDTO;
import com.backend.bcp.app.Pago.Application.dto.in.PagoPendienteDTO;
import com.backend.bcp.app.Pago.Application.dto.in.PagoRequestDTO;
import com.backend.bcp.app.Pago.Application.ports.in.RealizarPagoUseCase;
import com.backend.bcp.app.Shared.Infraestructure.config.ApiResponse;

import java.util.List;

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
    public ResponseEntity<ApiResponse<List<PagoPendienteDTO>>> listarPagosPendientes(@PathVariable String dni) {
        try {
            List<PagoPendienteDTO> pendientes = realizarPagoUseCase.listarPagosPendientes(dni);
            return ResponseEntity.ok(ApiResponse.success("Pagos pendientes listados", pendientes));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error al listar pagos: " + e.getMessage(), null));
        }
    }
    @PostMapping("/realizar")
    public ResponseEntity<ApiResponse<ComprobanteDTO>> realizarPago(@RequestBody PagoRequestDTO request) {
        try {
            ComprobanteDTO comprobante = realizarPagoUseCase.realizarPago(
                request.cuentaId(),
                request.pagoId()
            );
            return ResponseEntity.ok(ApiResponse.success("Pago realizado con éxito", comprobante));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error interno al procesar el pago", null));
        }
    }
}

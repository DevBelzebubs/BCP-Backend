package com.backend.bcp.app.Cuenta.Infraestructure.adapters.in;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.bcp.app.Comprobante.Application.dto.ComprobanteDTO;
import com.backend.bcp.app.Cuenta.Application.dto.in.CuentaDTO;
import com.backend.bcp.app.Cuenta.Application.dto.in.DetalleCuentaDTO;
import com.backend.bcp.app.Cuenta.Application.dto.in.TransferenciaRequestDTO;
import com.backend.bcp.app.Cuenta.Application.ports.in.GestionCuentaUseCase;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
//WORKS!
@RestController
@RequestMapping("/api/cuentas")
public class CuentaController {
    private final GestionCuentaUseCase gestionCuentaUseCase;

    public CuentaController(GestionCuentaUseCase gestionCuentaUseCase) {
        this.gestionCuentaUseCase = gestionCuentaUseCase;
    }
    @GetMapping("/usuario/{usuarioId}/listar")
    public ResponseEntity<List<CuentaDTO>> listarCuentasDisponibles(@PathVariable Long usuarioId) {
        List<CuentaDTO> cuentas = gestionCuentaUseCase.listarCuentasPorUsuario(usuarioId);
        return ResponseEntity.ok(cuentas);
    }
    @GetMapping("/{cuentaId}")
    public ResponseEntity<?> obtenerDetalle(@PathVariable Long cuentaId) {
    try {
        DetalleCuentaDTO detalleCuentaDTO = gestionCuentaUseCase.obtenerDetalleCuenta(cuentaId);
        if (detalleCuentaDTO == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontró el detalle de la cuenta para el usuario con ID: " + cuentaId);
        }
        return ResponseEntity.ok(detalleCuentaDTO);
    } catch (IllegalArgumentException e) {
        return ResponseEntity.badRequest()
                .body("ID de usuario inválido: " + e.getMessage());
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al obtener el detalle de la cuenta: " + e.getMessage());
    }
}
    @PostMapping("/{idCuentaOrigen}/transferir")
    public ResponseEntity<?> iniciarTransferencia(@PathVariable Long idCuentaOrigen, @RequestBody TransferenciaRequestDTO transferenciaRequestDTO) {
        try {
            gestionCuentaUseCase.iniciarTransferencia(idCuentaOrigen, transferenciaRequestDTO.idCuentaDestino(), transferenciaRequestDTO.monto());
            return ResponseEntity.ok(java.util.Map.of("mensaje", "Transferencia iniciada. OTP enviado al cliente."));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(java.util.Map.of("error", e.getMessage()));
        }
    }
    @PostMapping("/confirmar-transferencia")
    public ResponseEntity<?> postMethodName(@RequestParam Long clienteId, @RequestParam String codigoOTP){
        try {
            ComprobanteDTO comprobante = gestionCuentaUseCase.confirmarTransferencia(clienteId,codigoOTP);
            return ResponseEntity.ok(comprobante);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(java.util.Map.of("error", e.getMessage()));
        }
    }
    @PostMapping("/usuario/{usuarioId}/crear")
    public ResponseEntity<?> crearNuevaCuenta(@PathVariable Long usuarioId, @RequestBody CuentaDTO cuentaDTO) {
        try {
            CuentaDTO cuentaCreada = gestionCuentaUseCase.crearCuenta(cuentaDTO, usuarioId);
            return new ResponseEntity<>(cuentaCreada, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
package com.backend.bcp.app.Cuenta.Infraestructure.adapters.in;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.bcp.app.Comprobante.Application.dto.ComprobanteDTO;
import com.backend.bcp.app.Cuenta.Application.dto.in.DetalleCuentaDTO;
import com.backend.bcp.app.Cuenta.Application.dto.in.TransferenciaRequestDTO;
import com.backend.bcp.app.Cuenta.Application.ports.in.GestionCuentaUseCase;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/cuentas")
public class CuentaController {
    private final GestionCuentaUseCase gestionCuentaUseCase;

    public CuentaController(GestionCuentaUseCase gestionCuentaUseCase) {
        this.gestionCuentaUseCase = gestionCuentaUseCase;
    }
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<DetalleCuentaDTO> obtenerDetalle(@PathVariable Long usuarioid) {
        DetalleCuentaDTO detalleCuentaDTO = gestionCuentaUseCase.obtenerDetalleCuenta(usuarioid);
        return ResponseEntity.ok(detalleCuentaDTO);
    }
    @PostMapping("/{idCuentaOrigen}/transferir")
    public ResponseEntity<?> iniciarTransferencia(@PathVariable Long idCuentaOrigen, @RequestBody TransferenciaRequestDTO transferenciaRequestDTO) {
        gestionCuentaUseCase.iniciarTransferencia(idCuentaOrigen, transferenciaRequestDTO.idCuentaDestino(), transferenciaRequestDTO.monto());
        return ResponseEntity.ok(java.util.Map.of("mensaje", "Transferencia iniciada. OTP enviado al cliente."));
    }
    @PostMapping("/confirmar-transferencia")
    public ResponseEntity<ComprobanteDTO> postMethodName(@RequestParam String codigoOTP){
        ComprobanteDTO comprobante = gestionCuentaUseCase.confirmarTransferencia(codigoOTP);
        return ResponseEntity.ok(comprobante);
    }
}
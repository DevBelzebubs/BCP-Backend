package com.backend.bcp.app.Cuenta.Infraestructure.adapters.in;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.bcp.app.Comprobante.Application.dto.ComprobanteDTO;
import com.backend.bcp.app.Cuenta.Application.dto.in.CuentaDTO;
import com.backend.bcp.app.Cuenta.Application.dto.in.DetalleCuentaDTO;
import com.backend.bcp.app.Cuenta.Application.dto.in.TransferenciaRequestDTO;
import com.backend.bcp.app.Cuenta.Application.ports.in.GestionCuentaUseCase;
import com.backend.bcp.app.Shared.Infraestructure.config.ApiResponse;

import java.util.List;

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

    @GetMapping("/cliente/{dni}/listar")
    public ResponseEntity<ApiResponse<List<CuentaDTO>>> listarCuentasDisponibles(@PathVariable String dni) {
        List<CuentaDTO> cuentas = gestionCuentaUseCase.listarCuentasPorUsuario(dni);
        return ResponseEntity.ok(ApiResponse.success("Cuentas listadas correctamente", cuentas));
    }

    @GetMapping("/{cuentaId}")
    public ResponseEntity<ApiResponse<DetalleCuentaDTO>> obtenerDetalle(@PathVariable Long cuentaId) {
        try {
            DetalleCuentaDTO detalleCuentaDTO = gestionCuentaUseCase.obtenerDetalleCuenta(cuentaId);
            if (detalleCuentaDTO == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("No se encontró el detalle de la cuenta", null));
            }
            return ResponseEntity.ok(ApiResponse.success("Detalle obtenido", detalleCuentaDTO));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage(), null));
        }
    }

    @PostMapping("/{idCuentaOrigen}/transferir")
    public ResponseEntity<ApiResponse<Object>> iniciarTransferencia(@PathVariable Long idCuentaOrigen, @RequestBody TransferenciaRequestDTO transferenciaRequestDTO) {
        try {
            gestionCuentaUseCase.iniciarTransferencia(idCuentaOrigen, transferenciaRequestDTO.idCuentaDestino(), transferenciaRequestDTO.monto());
            return ResponseEntity.ok(ApiResponse.success("Transferencia iniciada. OTP enviado al cliente.", null));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage(), null));
        }
    }

    @PostMapping("/confirmar-transferencia")
    public ResponseEntity<ApiResponse<ComprobanteDTO>> confirmarTransferencia(@RequestParam String dni, @RequestParam String codigoOTP){
        try {
            ComprobanteDTO comprobante = gestionCuentaUseCase.confirmarTransferencia(dni,codigoOTP);
            return ResponseEntity.ok(ApiResponse.success("Transferencia confirmada exitosamente", comprobante));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage(), null));
        }
    }

    @PostMapping("/usuario/{dni}/crear")
    public ResponseEntity<ApiResponse<CuentaDTO>> crearNuevaCuenta(@PathVariable String dni, @RequestBody CuentaDTO cuentaDTO) {
        try {
            CuentaDTO cuentaCreada = gestionCuentaUseCase.crearCuenta(cuentaDTO, dni);
            return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Cuenta creada exitosamente", cuentaCreada));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage(), null));
        }
    }
}
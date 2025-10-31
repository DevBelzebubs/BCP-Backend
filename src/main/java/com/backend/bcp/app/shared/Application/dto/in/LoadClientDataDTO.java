package com.backend.bcp.app.Shared.Application.dto.in;

import java.util.List;

import com.backend.bcp.app.Cuenta.Application.dto.in.DetalleCuentaDTO;
import com.backend.bcp.app.Pago.Application.dto.in.PagoPendienteDTO;
import com.backend.bcp.app.Shared.Application.Security.dto.in.UsuarioDTO;

public record LoadClientDataDTO(
    UsuarioDTO informacionUsuario, 
    List<DetalleCuentaDTO> cuentas,
    List<PagoPendienteDTO> pagosPendientes) {

}

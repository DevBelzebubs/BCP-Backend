package com.backend.bcp.app.Usuario.Application.ports.in.Empleado;

import com.backend.bcp.app.Comprobante.Application.dto.ComprobanteDTO;
import com.backend.bcp.app.Usuario.Application.dto.in.Empleado.DepositoVentanillaDTO;
import com.backend.bcp.app.Usuario.Application.dto.in.Empleado.PagoVentanillaDTO;
import com.backend.bcp.app.Usuario.Application.dto.in.Empleado.RetiroVentanillaDTO;

public interface GestionOperacionVentanillaUseCase {
    ComprobanteDTO registrarDepositoVentanilla(DepositoVentanillaDTO request, Long empleadoId);

    ComprobanteDTO registrarRetiroVentanilla(RetiroVentanillaDTO request, Long empleadoId);

    ComprobanteDTO registrarPagoVentanilla(PagoVentanillaDTO request, Long empleadoId);
}

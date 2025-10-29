package com.backend.bcp.app.Comprobante.Application.ports.out;

import java.math.BigDecimal;

import com.backend.bcp.app.Comprobante.Application.dto.ComprobanteDTO;
import com.backend.bcp.app.Comprobante.Domain.Comprobante;

public interface ComprobanteRepository {
    ComprobanteDTO generarComprobante(String detalle, BigDecimal monto, String codigoAutorizacion);
    Comprobante guardarComprobante(Comprobante comprobante);
}

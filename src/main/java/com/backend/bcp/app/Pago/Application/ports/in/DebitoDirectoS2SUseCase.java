package com.backend.bcp.app.Pago.Application.ports.in;

import com.backend.bcp.app.Comprobante.Application.dto.ComprobanteDTO;
import com.backend.bcp.app.Pago.Application.dto.in.payflow.DebitoDirectoRequestDTO;

public interface DebitoDirectoS2SUseCase {
    ComprobanteDTO ejecutarDebito(DebitoDirectoRequestDTO request);
}

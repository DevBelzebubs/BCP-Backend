package com.backend.bcp.app.Comprobante.Application.ports.out;

import com.backend.bcp.app.Comprobante.Domain.Comprobante;

public interface ComprobanteRepository {
    void guardarComprobante(Comprobante comprobante);
}

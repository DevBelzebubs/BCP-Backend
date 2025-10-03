package com.backend.bcp.app.Comprobante.Aplication.ports.out;

import com.backend.bcp.app.Comprobante.Domain.Comprobante;

public interface ComprobanteRepository {
    void guardarComprobante(Comprobante comprobante);
}

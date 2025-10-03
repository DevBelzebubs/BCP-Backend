package com.backend.bcp.Comprobante.Aplication.ports.out;

import com.backend.bcp.Comprobante.Domain.Comprobante;

public interface ComprobanteRepository {
    void guardarComprobante(Comprobante comprobante);
}

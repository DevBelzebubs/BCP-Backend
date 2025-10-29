package com.backend.bcp.app.Comprobante.Application.ports.out;


import com.backend.bcp.app.Comprobante.Domain.Comprobante;

public interface ComprobanteRepository {
    Comprobante guardarComprobante(Comprobante comprobante);
}

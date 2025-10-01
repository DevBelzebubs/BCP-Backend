package com.backend.bcp.aplicacion.ports.UseCases.RealizacionPagos.out;

import com.backend.bcp.dominio.Comprobante;

public interface ComprobanteRepository {
    void guardarComprobante(Comprobante comprobante);
}

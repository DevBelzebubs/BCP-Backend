package com.backend.bcp.aplicacion.ports.UseCases.TransferirFondos.out;

import com.backend.bcp.Comprobante.Domain.Comprobante;

public interface ComprobanteService {
    Comprobante generarComprobante(Long idTransaccion);
}

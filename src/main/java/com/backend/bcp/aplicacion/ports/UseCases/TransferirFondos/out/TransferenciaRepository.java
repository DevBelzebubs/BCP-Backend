package com.backend.bcp.aplicacion.ports.UseCases.TransferirFondos.out;

public interface TransferenciaRepository {
    void registrarTransferencia(Long cuentaOrigenId, Long cuentaDestinoId, Double monto);
}

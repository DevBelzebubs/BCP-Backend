package com.backend.bcp.app.Transaccion.Aplication.ports.out;

public interface OtpService {
    void generarEnviarOtp(Long idCliente);
    boolean validarOtp(Long idCliente, String codigoOtp);
}
package com.backend.bcp.app.Transaccion.Application.ports.out;

public interface OtpService {
    void generarEnviarOtp(Long idCliente);
    boolean validarOtp(Long idCliente, String codigoOtp);
}
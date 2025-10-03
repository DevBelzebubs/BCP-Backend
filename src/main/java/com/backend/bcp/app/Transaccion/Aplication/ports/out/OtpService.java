package com.backend.bcp.app.Transaccion.Aplication.ports.out;

public interface OtpService {
    void generarEnviarOtp(Long idUsuario);
    boolean validarOtp(Long idUsuario, String codigoOtp);
}
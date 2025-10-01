package com.backend.bcp.aplicacion.ports.UseCases.TransferirFondos.out;

public interface OtpService {
    void generarEnviarOtp(Long idUsuario);
    boolean validarOtp(Long idUsuario, String codigoOtp);
}

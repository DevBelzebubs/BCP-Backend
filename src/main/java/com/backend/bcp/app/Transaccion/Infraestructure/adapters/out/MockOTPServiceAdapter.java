package com.backend.bcp.app.Transaccion.Infraestructure.adapters.out;

import org.springframework.stereotype.Service;

import com.backend.bcp.app.Transaccion.Aplication.ports.out.OtpService;

@Service
public class MockOTPServiceAdapter implements OtpService {
    private static final String MOCK_OTP = "123456"; 
    @Override
    public void generarEnviarOtp(Long idCliente) {
        System.out.println("SIMULACIÓN OTP: OTP generado y enviado a Cliente " + idCliente + ". Código: " + MOCK_OTP);

    }

    @Override
    public boolean validarOtp(Long idCliente, String codigoOtp) {
        boolean isValid = MOCK_OTP.equals(codigoOtp);
        if (!isValid) {
            System.err.println("VALIDACIÓN OTP: Fallida para Cliente " + idCliente);
        }
        return isValid;
    }

}

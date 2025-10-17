package com.backend.bcp.app.Transaccion.Infraestructure.adapters.out;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.backend.bcp.app.Transaccion.Application.ports.out.OtpService;

@Service
public class MockOTPServiceAdapter implements OtpService {
    private final ConcurrentHashMap<Long, String> clientOtpCache = new ConcurrentHashMap<>();
    private final Random random = new Random(); 
    private String generateRandomOtp() {
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    public String getLastOtp(Long idCliente) {
        return clientOtpCache.get(idCliente);
    }
    @Override
    public void generarEnviarOtp(Long idCliente) {
        String dynamicOtp = generateRandomOtp();
        clientOtpCache.put(idCliente, dynamicOtp);
        System.out.println("--- DYNAMIC OTP SIMULATION ---");
        System.out.println("OTP generado y enviado a Cliente ID: " + idCliente);
        System.out.println("CÓDIGO: " + dynamicOtp);
        System.out.println("------------------------------");
    }

    @Override
    public boolean validarOtp(Long idCliente, String codigoOtp) {
        String storedOtp = clientOtpCache.get(idCliente);
        
        if (storedOtp != null && storedOtp.equals(codigoOtp)) {
            clientOtpCache.remove(idCliente); // El OTP solo sirve una vez
            return true;
        }
        return false;
    }

}

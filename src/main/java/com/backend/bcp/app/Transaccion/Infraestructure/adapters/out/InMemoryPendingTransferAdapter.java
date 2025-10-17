package com.backend.bcp.app.Transaccion.Infraestructure.adapters.out;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import com.backend.bcp.app.Transaccion.Application.dto.in.PendingTransferDTO;
import com.backend.bcp.app.Transaccion.Application.ports.out.PendingTransferRepository;

@Repository
public class InMemoryPendingTransferAdapter implements PendingTransferRepository {
    private final ConcurrentHashMap<Long, PendingTransferDTO> cache = new ConcurrentHashMap<>();
    
    @Override
    public String savePendingTransfer(PendingTransferDTO transferDTO) {
        cache.put(transferDTO.idCliente(), transferDTO);
        return "OK";
    }

    @Override
    public Optional<PendingTransferDTO> getTransferByOtpKey(String otpKey) {
        try {
            Long idCliente = Long.parseLong(otpKey);
            return Optional.ofNullable(cache.get(idCliente));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    @Override
    public void deleteTransfer(String otpKey) {
        try {
            Long idCliente = Long.parseLong(otpKey);
            cache.remove(idCliente);
        } catch (NumberFormatException e) {
        }
    }
    public void deleteTransferByClientId(String otpKey) {
        try {
            Long idCliente = Long.parseLong(otpKey);
            cache.remove(idCliente);
        } catch (NumberFormatException e) {
            // No se puede eliminar si la clave no es un ID válido.
        }
    }

    @Override
    public void updatePendingTransfer(PendingTransferDTO transferDTO) {
        cache.put(transferDTO.idCliente(), transferDTO);
    }
}

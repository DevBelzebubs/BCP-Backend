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
        return Optional.ofNullable(cache.get(otpKey));
    }

    @Override
    public void deleteTransfer(String otpKey) {
        
    }
    public void deleteTransferByClientId(Long idCliente) {
        cache.remove(idCliente);
    }
}

package com.backend.bcp.app.Transaccion.Application.ports.out;

import java.util.Optional;

import com.backend.bcp.app.Transaccion.Application.dto.in.PendingTransferDTO;

public interface PendingTransferRepository {
    String savePendingTransfer(PendingTransferDTO transferDTO);
    Optional<PendingTransferDTO> getTransferByOtpKey(String otpKey);
    void deleteTransfer(String otpKey);
    void updatePendingTransfer(PendingTransferDTO transferDTO);
}

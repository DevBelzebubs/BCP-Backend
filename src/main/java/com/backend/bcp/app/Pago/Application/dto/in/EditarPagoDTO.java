package com.backend.bcp.app.Pago.Application.dto.in;

import java.math.BigDecimal;

public class EditarPagoDTO {
    private BigDecimal monto;
    private Long servicioId;

    public BigDecimal getMonto() {
        return monto;
    }
    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }
    public Long getServicioId() {
        return servicioId;
    }
    public void setServicioId(Long servicioId) {
        this.servicioId = servicioId;
    }
}

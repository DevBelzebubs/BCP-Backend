package com.backend.bcp.app.Prestamo.Application.dto.in;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class SolicitudCreditoDTO {

    @NotNull(message = "El ID de usuario no puede ser nulo")
    @JsonProperty("usuarioId")
    private Long usuarioId;

    @NotNull(message = "El monto no puede ser nulo")
    @Min(value = 1, message = "El monto debe ser mayor a cero")
    @JsonProperty("monto")
    private BigDecimal monto;

    @NotNull(message = "El plazo en meses no puede ser nulo")
    @Min(value = 1, message = "El plazo debe ser de al menos 1 mes")
    @JsonProperty("plazoMeses")
    private Integer plazoMeses;

    public SolicitudCreditoDTO() {
    }
    @JsonCreator
    public SolicitudCreditoDTO(
            @JsonProperty("usuarioId") Long usuarioId,
            @JsonProperty("monto") BigDecimal monto,
            @JsonProperty("plazoMeses") Integer plazoMeses) {
        this.usuarioId = usuarioId;
        this.monto = monto;
        this.plazoMeses = plazoMeses;
    }

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
    public BigDecimal getMonto() { return monto; }
    public void setMonto(BigDecimal monto) { this.monto = monto; }
    public Integer getPlazoMeses() { return plazoMeses; }
    public void setPlazoMeses(Integer plazoMeses) { this.plazoMeses = plazoMeses; }
}
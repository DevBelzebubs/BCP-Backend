package com.backend.bcp.app.Prestamo.Application.dto.in;

import com.fasterxml.jackson.annotation.JsonProperty; // Importar
import java.math.BigDecimal;
// QUITA import jakarta.validation.constraints... por ahora

public class SolicitudCreditoDTO {

    // @NotNull <-- Quita validación temporalmente
    @JsonProperty("usuarioId") // Añadir JsonProperty
    private Long usuarioId;

    // @NotNull <-- Quita validación temporalmente
    // @Min(...) <-- Quita validación temporalmente
    @JsonProperty("monto") // Añadir JsonProperty
    private BigDecimal monto;

    // @NotNull <-- Quita validación temporalmente
    // @Min(...) <-- Quita validación temporalmente
    @JsonProperty("plazoMeses") // Añadir JsonProperty
    private Integer plazoMeses;

    // Constructor vacío
    public SolicitudCreditoDTO() {
    }

    // Getters y Setters (sin cambios)
    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
    public BigDecimal getMonto() { return monto; }
    public void setMonto(BigDecimal monto) { this.monto = monto; }
    public Integer getPlazoMeses() { return plazoMeses; }
    public void setPlazoMeses(Integer plazoMeses) { this.plazoMeses = plazoMeses; }
}
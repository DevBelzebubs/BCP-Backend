package com.backend.bcp.app.Prestamo.Application.dto.out;

import java.math.BigDecimal;

public class PrestamoResponseDTO {

    private Long id;
    private Long usuarioId;
    private BigDecimal monto;
    private int plazoMeses;
    private double interes;
    private String estado;

    // Constructor vacío
    public PrestamoResponseDTO() {
    }

    // Constructor completo
    public PrestamoResponseDTO(Long id, Long usuarioId, BigDecimal monto, int plazoMeses, double interes, String estado) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.monto = monto;
        this.plazoMeses = plazoMeses;
        this.interes = interes;
        this.estado = estado;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
    public BigDecimal getMonto() { return monto; }
    public void setMonto(BigDecimal monto) { this.monto = monto; }
    public int getPlazoMeses() { return plazoMeses; }
    public void setPlazoMeses(int plazoMeses) { this.plazoMeses = plazoMeses; }
    public double getInteres() { return interes; }
    public void setInteres(double interes) { this.interes = interes; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
package com.backend.bcp.app.Comprobante.Infraestructure.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Comprobante")
public class ComprobanteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Comprobante")
    private Long IdComprobante;
    @Column(name = "Servicio")
    private String servicio;
    @Column(name = "Monto_Pagado")
    private BigDecimal montoPagado;
    @Column(name = "Fecha")
    private LocalDate fecha;
    @Column(name = "Codigo_Autorizacion")
    private String codigoAutorizacion;
    public Long getIdComprobante() {
        return IdComprobante;
    }
    public void setIdComprobante(Long idComprobante) {
        IdComprobante = idComprobante;
    }
    public String getServicio() {
        return servicio;
    }
    public void setServicio(String servicio) {
        this.servicio = servicio;
    }
    public BigDecimal getMontoPagado() {
        return montoPagado;
    }
    public void setMontoPagado(BigDecimal montoPagado) {
        this.montoPagado = montoPagado;
    }
    public LocalDate getFecha() {
        return fecha;
    }
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
    public String getCodigoAutorizacion() {
        return codigoAutorizacion;
    }
    public void setCodigoAutorizacion(String codigoAutorizacion) {
        this.codigoAutorizacion = codigoAutorizacion;
    }
    
}

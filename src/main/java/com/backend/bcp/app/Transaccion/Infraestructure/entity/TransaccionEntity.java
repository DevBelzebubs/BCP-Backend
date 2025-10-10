package com.backend.bcp.app.Transaccion.Infraestructure.entity;


import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.backend.bcp.app.Cuenta.Infraestructure.entity.CuentaEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Transaccion")
public class TransaccionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Transaccion")
    private Long idTransaccion;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_Cuenta", referencedColumnName = "ID_Cuenta")
    private CuentaEntity cuenta;
    @Column(name = "Tipo")
    private String tipo;
    @Column(name = "Monto")
    private BigDecimal monto;
    @Column(name = "Fecha")
    private LocalDateTime fecha;
    
    public Long getIdTransaccion() {
        return idTransaccion;
    }
    public void setIdTransaccion(Long idTransaccion) {
        this.idTransaccion = idTransaccion;
    }
    public CuentaEntity getCuenta() {
        return cuenta;
    }
    public void setCuenta(CuentaEntity cuenta) {
        this.cuenta = cuenta;
    }
    public BigDecimal getMonto() {
        return monto;
    }
    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }
    public LocalDateTime getFecha() {
        return fecha;
    }
    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
}

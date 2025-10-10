package com.backend.bcp.app.Cuenta.Infraestructure.entity;

import java.math.BigDecimal;

import com.backend.bcp.app.Usuario.Infraestructure.entity.cliente.ClienteEntity;

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
@Table(name = "Cuenta")
public class CuentaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Cuenta")
    private Long idCuenta;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_Cliente", referencedColumnName = "ID_Cliente")
    private ClienteEntity cliente;
    @Column(name = "Tipo_Cuenta")
    private String tipoCuenta;
    @Column(name = "Estado_Cuenta")
    private String estadoCuenta;
    @Column(name = "Numero_Cuenta", unique = true)
    private String numeroCuenta;
    @Column(name = "Saldo")
    private BigDecimal saldo;
    public Long getIdCuenta() {
        return idCuenta;
    }
    public void setIdCuenta(Long idCuenta) {
        this.idCuenta = idCuenta;
    }
    public String getTipoCuenta() {
        return tipoCuenta;
    }
    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }
    public String getEstadoCuenta() {
        return estadoCuenta;
    }
    public void setEstadoCuenta(String estadoCuenta) {
        this.estadoCuenta = estadoCuenta;
    }
    public String getNumeroCuenta() {
        return numeroCuenta;
    }
    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }
    public BigDecimal getSaldo() {
        return saldo;
    }
    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }
    public ClienteEntity getCliente() {
        return cliente;
    }
    public void setCliente(ClienteEntity cliente) {
        this.cliente = cliente;
    }
    
}

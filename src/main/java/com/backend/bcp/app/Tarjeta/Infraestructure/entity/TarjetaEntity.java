package com.backend.bcp.app.Tarjeta.Infraestructure.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.backend.bcp.app.Usuario.Infraestructure.entity.cliente.ClienteEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Tarjeta")
public class TarjetaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Tarjeta")
    private Long idTarjeta;
    @ManyToOne
    @JoinColumn(name = "ID_Cliente", referencedColumnName = "ID_CLIENTE")
    private ClienteEntity cliente;
    @Column(name = "Num_tarjeta")
    private String numeroTarjeta;
    @Column(name = "Fecha_Vencimiento")
    private LocalDate fechaVencimiento;
    @Column(name = "Saldo")
    private BigDecimal saldo;
    
    public void setIdTarjeta(Long idTarjeta) {
        this.idTarjeta = idTarjeta;
    }
    public ClienteEntity getCliente() {
        return cliente;
    }
    public void setCliente(ClienteEntity cliente) {
        this.cliente = cliente;
    }
    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }
    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }
    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }
    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }
    public BigDecimal getSaldo() {
        return saldo;
    }
    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }
    public long getIdTarjeta() {
        return idTarjeta;
    }
    public void setIdTarjeta(long idTarjeta) {
        this.idTarjeta = idTarjeta;
    }

    
}

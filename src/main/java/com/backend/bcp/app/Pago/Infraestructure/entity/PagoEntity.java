package com.backend.bcp.app.Pago.Infraestructure.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.backend.bcp.app.Usuario.Infraestructure.entity.cliente.ClienteEntity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Pago")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TIPO_PAGO", discriminatorType = DiscriminatorType.STRING)
public abstract class PagoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Pago")
    private Long idPago;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_Cliente", referencedColumnName = "ID_Cliente")
    private ClienteEntity cliente; 
    @Column(name = "Monto")
    private BigDecimal monto;
    @Column(name = "Fecha")
    private LocalDate fecha;
    @Column(name = "Estado")
    private String estado;
    //Id para la otra app
    @Column(name = "payflow_service_id")
    private String payflowServiceId;

    public String getPayflowServiceId() {
        return payflowServiceId;
    }

    public void setPayflowServiceId(String payflowServiceId) {
        this.payflowServiceId = payflowServiceId;
    }
        
    public Long getIdPago() {
        return idPago;
    }
    public void setIdPago(Long idPago) {
        this.idPago = idPago;
    }
    public BigDecimal getMonto() {
        return monto;
    }
    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }
    public LocalDate getFecha() {
        return fecha;
    }
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public ClienteEntity getCliente() {
        return cliente;
    }
    public void setCliente(ClienteEntity cliente) {
        this.cliente = cliente;
    }
}

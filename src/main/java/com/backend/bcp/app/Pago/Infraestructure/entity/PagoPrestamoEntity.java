package com.backend.bcp.app.Pago.Infraestructure.entity;

import com.backend.bcp.app.Prestamo.Infraestructure.entity.PrestamoEntity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@DiscriminatorValue("PRESTAMO")
public class PagoPrestamoEntity extends PagoEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_Prestamo", referencedColumnName = "ID_Prestamos")
    private PrestamoEntity prestamo;

    public PrestamoEntity getPrestamo() {
        return prestamo;
    }

    public void setPrestamo(PrestamoEntity prestamo) {
        this.prestamo = prestamo;
    }
    
}

package com.backend.bcp.app.Pago.Infraestructure.entity;

import com.backend.bcp.app.Servicio.Infraestructure.entity.ServicioEntity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@DiscriminatorValue("SERVICIO")
public class PagoServicioEntity extends PagoEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_Servicio", referencedColumnName = "ID_Servicio")
    private ServicioEntity servicio;

    public ServicioEntity getServicio() {
        return servicio;
    }

    public void setServicio(ServicioEntity servicio) {
        this.servicio = servicio;
    }
    
}

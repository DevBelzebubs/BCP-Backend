package com.backend.bcp.app.Usuario.Infraestructure.entity.empleado;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Backoffice")
public class BackOfficeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Backoffice")
    private Long idBackoffice;
    @OneToOne()
    @JoinColumn(name = "ID_Empleado", referencedColumnName="ID_Empleado")
    private EmpleadoEntity idEmpleado;
    
    public Long getIdBackoffice() {
        return idBackoffice;
    }

    public void setIdBackoffice(Long idBackoffice) {
        this.idBackoffice = idBackoffice;
    }

    public EmpleadoEntity getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(EmpleadoEntity idEmpleado) {
        this.idEmpleado = idEmpleado;
    }
    
}

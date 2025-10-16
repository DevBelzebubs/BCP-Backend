package com.backend.bcp.app.Usuario.Infraestructure.entity.empleado;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Admin")
public class AdminEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Admin")
    private long idAdmin;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_Empleado", referencedColumnName="ID_Empleado")
    private EmpleadoEntity idEmpleado;
    public long getIdAdmin() {
        return idAdmin;
    }
    public void setIdAdmin(long idAdmin) {
        this.idAdmin = idAdmin;
    }
    public EmpleadoEntity getIdEmpleado() {
        return idEmpleado;
    }
    public void setIdEmpleado(EmpleadoEntity idEmpleado) {
        this.idEmpleado = idEmpleado;
    }
    
}

package com.backend.bcp.app.Usuario.Infraestructure.entity.empleado;


import java.time.LocalDate;

import com.backend.bcp.app.shared.Infraestructure.entity.UsuarioEntity;

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
@Table(name = "empleado")
public class EmpleadoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Empleado")
    private Long idEmpleado;
    @Column(name = "Fecha_Contratacion")
    private LocalDate fechaContratacion;
    @Column(name = "Salario")
    private double salario;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_Usuario", referencedColumnName = "ID_Usuario")
    private UsuarioEntity idUsuario;
    
    public double getSalario() {
        return salario;
    }
    public void setSalario(double salario) {
        this.salario = salario;
    }
    public UsuarioEntity getIdUsuario() {
        return idUsuario;
    }
    public void setIdUsuario(UsuarioEntity idUsuario) {
        this.idUsuario = idUsuario;
    }
    public Long getIdEmpleado() {
        return idEmpleado;
    }
    public void setIdEmpleado(Long idEmpleado) {
        this.idEmpleado = idEmpleado;
    }
    public LocalDate getFechaContratacion() {
        return fechaContratacion;
    }
    public void setFechaContratacion(LocalDate fechaContratacion) {
        this.fechaContratacion = fechaContratacion;
    }

}

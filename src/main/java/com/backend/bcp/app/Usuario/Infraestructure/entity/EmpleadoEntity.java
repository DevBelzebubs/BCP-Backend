package com.backend.bcp.app.Usuario.Infraestructure.entity;


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
    private Long id;
    @Column(name = "Fecha_Contratacion")
    private String fechaContratacion;
    @Column(name = "Salario")
    private double salario;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Usuario_ID_Usuario", referencedColumnName = "idUsuario")
    private Long idUsuario;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getFechaContratacion() {
        return fechaContratacion;
    }
    public void setFechaContratacion(String fechaContratacion) {
        this.fechaContratacion = fechaContratacion;
    }
    public double getSalario() {
        return salario;
    }
    public void setSalario(double salario) {
        this.salario = salario;
    }
    public Long getIdUsuario() {
        return idUsuario;
    }
    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }
    
}

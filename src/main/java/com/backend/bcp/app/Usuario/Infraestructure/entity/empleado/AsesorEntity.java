package com.backend.bcp.app.Usuario.Infraestructure.entity.empleado;

import com.backend.bcp.app.Prestamo.Infraestructure.entity.PrestamoEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Asesor")
public class AsesorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Asesor")
    private Long idAsesor;
    @OneToOne()
    @JoinColumn(name = "ID_Empleado", referencedColumnName="ID_Empleado")
    private EmpleadoEntity idEmpleado;
    @OneToOne()
    @JoinColumn(name = "ID_Prestamos", referencedColumnName = "ID_Prestamos")
    private PrestamoEntity idPrestamo;
    public EmpleadoEntity getIdEmpleado() {
        return idEmpleado;
    }
    public void setIdEmpleado(EmpleadoEntity idEmpleado) {
        this.idEmpleado = idEmpleado;
    }
    public PrestamoEntity getIdPrestamo() {
        return idPrestamo;
    }
    public void setIdPrestamo(PrestamoEntity idPrestamo) {
        this.idPrestamo = idPrestamo;
    }
    public Long getIdAsesor() {
        return idAsesor;
    }
    public void setIdAsesor(Long idAsesor) {
        this.idAsesor = idAsesor;
    }
}

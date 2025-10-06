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
@Table(name = "Asesor")
public class AsesorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Asesor")
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_Empleado", referencedColumnName="idEmpleado")
    @Column(name = "ID_Empleado")
    private Long idEmpleado;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_Prestamos", referencedColumnName = "idPrestamo")
    @Column(name = "ID_Prestamo")
    private Long idPrestamo;
}

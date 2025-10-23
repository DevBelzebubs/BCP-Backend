package com.backend.bcp.app.Usuario.Domain;

import java.time.LocalDate;

import com.backend.bcp.app.Shared.Domain.Usuario;

public class Empleado extends Usuario {
    private double pago;
    private LocalDate fechaContratacion;
    
    public Empleado(double pago, LocalDate fechaContratacion) {
        this.pago = pago;
        this.fechaContratacion = fechaContratacion;
    }

    public Empleado(String nombre, String contrasena, double pago, LocalDate fechaContratacion) {
        super(nombre, contrasena);
        this.pago = pago;
        this.fechaContratacion = fechaContratacion;
    }

    public Empleado(Long id, String nombre, String contrasena, String correo, String dni, String direccion,
            String telefono, double pago, LocalDate fechaContratacion) {
        super(id, nombre, contrasena, correo, dni, direccion, telefono);
        this.pago = pago;
        this.fechaContratacion = fechaContratacion;
    }

    public Empleado(){}
    
    public double getPago() {
        return pago;
    }
    public void setPago(double pago) {
        this.pago = pago;
    }
    public LocalDate getFechaContratacion() {
        return fechaContratacion;
    }
    public void setFechaContratacion(LocalDate fechaContratacion) {
        this.fechaContratacion = fechaContratacion;
    }
    
    
}

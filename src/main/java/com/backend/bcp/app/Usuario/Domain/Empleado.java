package com.backend.bcp.app.Usuario.Domain;

import com.backend.bcp.shared.Domain.Usuario;

public class Empleado extends Usuario {
    public double pago;
    public Empleado(Long id, String nombre, String contrasena, String correo,
                    String dni, String direccion, String telefono, double pago) {
        super(id, nombre, contrasena, correo, dni, direccion, telefono);
        this.pago = pago;
    }
    public double getPago() {
        return pago;
    }
    public void setPago(double pago) {
        this.pago = pago;
    }
    
}

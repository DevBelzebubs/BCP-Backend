package com.backend.bcp.app.Usuario.Domain;

public class BackOffice extends Empleado {
    public BackOffice(Long id, String nombre, String contrasena, String correo,
                      String dni, String direccion, String telefono,double pago) {
        super(id, nombre, contrasena, correo, dni, direccion, telefono, pago);
    }

}

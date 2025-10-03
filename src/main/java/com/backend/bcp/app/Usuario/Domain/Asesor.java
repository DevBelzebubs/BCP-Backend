package com.backend.bcp.app.Usuario.Domain;

public class Asesor extends Empleado {
    public Asesor(Long id, String nombre, String contrasena, String correo,
                  String dni, String direccion, String telefono,double pago) {
        super(id, nombre, contrasena, correo, dni, direccion, telefono, pago);
    }

}

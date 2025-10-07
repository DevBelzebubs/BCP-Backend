package com.backend.bcp.app.Usuario.Domain;

import java.time.LocalDate;


public class Asesor extends Empleado {
    public Asesor(Long id, String nombre, String contrasena, String correo,
                  String dni, String direccion, String telefono,double pago, LocalDate fechaContratacion) {
        super(id, nombre, contrasena, correo, dni, direccion, telefono, pago, fechaContratacion);
    }
    public Asesor(Long id,String nombre, String contrasena, String Correo, String dni, String direccion, String telefono, LocalDate fechaContratacion, double salario){}
}

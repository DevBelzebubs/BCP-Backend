package com.backend.bcp.app.Usuario.Aplication.ports.out.Empleado;

import java.util.List;

import com.backend.bcp.app.Prestamo.Domain.Prestamo;
import com.backend.bcp.app.Servicio.Domain.Servicio;
import com.backend.bcp.app.Transaccion.Domain.Transaccion;

public interface EmpleadoAgenciaRepository {
    List<Transaccion> listDeposito();
    List<Transaccion> listRetiro();
    List<Servicio> listServicio();
    List<Prestamo> listPrestamo();
}

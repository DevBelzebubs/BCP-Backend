package com.backend.bcp.app.shared.Application.Security.ports.in;

import com.backend.bcp.app.Usuario.Infraestructure.entity.cliente.ClienteEntity;
import com.backend.bcp.app.Usuario.Infraestructure.entity.empleado.AsesorEntity;
import com.backend.bcp.app.Usuario.Infraestructure.entity.empleado.BackOfficeEntity;
import com.backend.bcp.app.Usuario.Infraestructure.entity.empleado.EmpleadoEntity;
import com.backend.bcp.app.shared.Application.Security.dto.in.ClienteDTO;
import com.backend.bcp.app.shared.Application.Security.dto.in.EmpleadoDTO;

public interface RegistroService {
    ClienteEntity registrarCliente(ClienteDTO dto);
    EmpleadoEntity registrarEmpleado(EmpleadoDTO dto);
    AsesorEntity registrarAsesor(EmpleadoDTO dto);
    BackOfficeEntity registrarBackOffice(EmpleadoDTO dto);
}

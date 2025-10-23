package com.backend.bcp.app.Shared.Application.Security.ports.in;

import com.backend.bcp.app.Shared.Application.Security.dto.in.ClienteDTO;
import com.backend.bcp.app.Shared.Application.Security.dto.in.EmpleadoDTO;
import com.backend.bcp.app.Usuario.Infraestructure.entity.cliente.ClienteEntity;
import com.backend.bcp.app.Usuario.Infraestructure.entity.empleado.AsesorEntity;
import com.backend.bcp.app.Usuario.Infraestructure.entity.empleado.BackOfficeEntity;
import com.backend.bcp.app.Usuario.Infraestructure.entity.empleado.EmpleadoEntity;

public interface RegistroService {
    ClienteEntity registrarCliente(ClienteDTO dto);
    EmpleadoEntity registrarEmpleado(EmpleadoDTO dto);
    AsesorEntity registrarAsesor(EmpleadoDTO dto);
    BackOfficeEntity registrarBackOffice(EmpleadoDTO dto);
}

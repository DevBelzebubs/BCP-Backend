package com.backend.bcp.shared.Aplication.Security.ports.in;

import com.backend.bcp.app.Usuario.Infraestructure.entity.cliente.ClienteEntity;
import com.backend.bcp.app.Usuario.Infraestructure.entity.empleado.EmpleadoEntity;
import com.backend.bcp.shared.Aplication.Security.dto.in.ClienteDTO;
import com.backend.bcp.shared.Aplication.Security.dto.in.EmpleadoDTO;

public interface RegistroService {
    ClienteEntity registrarCliente(ClienteDTO dto);
    EmpleadoEntity registrarEmpleado(EmpleadoDTO dto);   
}

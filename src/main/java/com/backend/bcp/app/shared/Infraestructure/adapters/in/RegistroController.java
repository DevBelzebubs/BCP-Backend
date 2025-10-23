package com.backend.bcp.app.Shared.Infraestructure.adapters.in;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.bcp.app.Shared.Application.Security.dto.in.ClienteDTO;
import com.backend.bcp.app.Shared.Application.Security.dto.in.EmpleadoDTO;
import com.backend.bcp.app.Shared.Application.Security.ports.in.RegistroService;
import com.backend.bcp.app.Usuario.Infraestructure.entity.cliente.ClienteEntity;
import com.backend.bcp.app.Usuario.Infraestructure.entity.empleado.AsesorEntity;
import com.backend.bcp.app.Usuario.Infraestructure.entity.empleado.BackOfficeEntity;
import com.backend.bcp.app.Usuario.Infraestructure.entity.empleado.EmpleadoEntity;

import jakarta.validation.Valid;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/registro")
public class RegistroController {
    private final RegistroService registroService;

    public RegistroController(RegistroService registroService) {
        this.registroService = registroService;
    }
    @PostMapping("/cliente")
    public ResponseEntity<ClienteEntity> registrarCliente (@Valid @RequestBody ClienteDTO dto) {
        ClienteEntity cliente = registroService.registrarCliente(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(cliente);
    }
    @PostMapping("/empleado")
    public ResponseEntity<?> registrarEmpleado(@RequestBody EmpleadoDTO dto) {
        try{
            EmpleadoEntity empleado = registroService.registrarEmpleado(dto);
            return ResponseEntity.ok(empleado);
        }catch(Exception e){
            e.fillInStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("Error", "Error interno"));
        }
    }
    @PostMapping("/asesor")
    public ResponseEntity<AsesorEntity> registrarAsesor(@Valid @RequestBody EmpleadoDTO dto) {
        AsesorEntity asesor = registroService.registrarAsesor(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(asesor);
    }
    @PostMapping("/backoffice")
    public ResponseEntity<BackOfficeEntity> registrarBackOffice(@Valid @RequestBody EmpleadoDTO dto) {
        BackOfficeEntity backOffice = registroService.registrarBackOffice(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(backOffice);
    }
}
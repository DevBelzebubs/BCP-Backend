package com.backend.bcp.app.shared.Infraestructure.adapters.in;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.bcp.app.Usuario.Infraestructure.entity.cliente.ClienteEntity;
import com.backend.bcp.app.Usuario.Infraestructure.entity.empleado.EmpleadoEntity;
import com.backend.bcp.app.shared.Application.Security.dto.in.ClienteDTO;
import com.backend.bcp.app.shared.Application.Security.dto.in.EmpleadoDTO;
import com.backend.bcp.app.shared.Application.Security.ports.in.RegistroService;

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
    public ResponseEntity<ClienteEntity> registrarCliente (@RequestBody ClienteDTO dto) {
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
}
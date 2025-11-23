package com.backend.bcp.app.Shared.Infraestructure.adapters.in;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.bcp.app.Shared.Application.Security.dto.in.ClienteDTO;
import com.backend.bcp.app.Shared.Application.Security.dto.in.EmpleadoDTO;
import com.backend.bcp.app.Shared.Application.Security.ports.in.RegistroService;
import com.backend.bcp.app.Shared.Infraestructure.config.ApiResponse;
import com.backend.bcp.app.Usuario.Infraestructure.entity.cliente.ClienteEntity;
import com.backend.bcp.app.Usuario.Infraestructure.entity.empleado.AsesorEntity;
import com.backend.bcp.app.Usuario.Infraestructure.entity.empleado.BackOfficeEntity;
import com.backend.bcp.app.Usuario.Infraestructure.entity.empleado.EmpleadoEntity;

import jakarta.validation.Valid;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

//WORKS!
@RestController
@RequestMapping("/api/registro")
public class RegistroController {
    private final RegistroService registroService;
    private static final Logger log = LoggerFactory.getLogger(RegistroController.class);
    public RegistroController(RegistroService registroService) {
        this.registroService = registroService;
    }

    @PostMapping("/cliente")
    public ResponseEntity<ApiResponse<ClienteEntity>> registrarCliente (@Valid @RequestBody ClienteDTO dto) {
        try {
            ClienteEntity cliente = registroService.registrarCliente(dto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Cliente registrado exitosamente", cliente));
        } catch (IllegalArgumentException | DataIntegrityViolationException e) {
            log.warn("Error de validación: {}", e.getMessage());
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage(), null));
        } catch (Exception e) {
            log.error("Error inesperado: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Ocurrió un error interno al registrar el cliente.", null));
        }
    }

    @PostMapping("/empleado")
    public ResponseEntity<ApiResponse<EmpleadoEntity>> registrarEmpleado(@Valid @RequestBody EmpleadoDTO dto) {
        try{
            EmpleadoEntity empleado = registroService.registrarEmpleado(dto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Empleado registrado exitosamente", empleado));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage(), null));
        }
    }

    @PostMapping("/asesor")
    public ResponseEntity<?> registrarAsesor(@Valid @RequestBody EmpleadoDTO dto) {
         try {
            AsesorEntity asesor = registroService.registrarAsesor(dto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Asesor registrado exitosamente", asesor));
        } catch (IllegalArgumentException | DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage(), null));
        } 
    }

    @PostMapping("/backoffice")
    public ResponseEntity<?> registrarBackOffice(@Valid @RequestBody EmpleadoDTO dto) {
         try {
            BackOfficeEntity backOffice = registroService.registrarBackOffice(dto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("BackOffice registrado exitosamente", backOffice));
        } catch (IllegalArgumentException | DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage(), null));
        }
    }
}
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
    public ResponseEntity<?> registrarCliente (@Valid @RequestBody ClienteDTO dto) {
        try {
            ClienteEntity cliente = registroService.registrarCliente(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(cliente);
        } catch (IllegalArgumentException | DataIntegrityViolationException e) {
            log.warn("Intento de registro de cliente fallido (duplicado u otro): {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            log.error("Error inesperado al registrar cliente: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(Map.of("error", "Ocurrió un error interno al registrar el cliente."));
        }
    }

    @PostMapping("/empleado")
    public ResponseEntity<?> registrarEmpleado(@Valid @RequestBody EmpleadoDTO dto) {
        try{
            EmpleadoEntity empleado = registroService.registrarEmpleado(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(empleado);
        } catch (IllegalArgumentException | DataIntegrityViolationException e) {
            log.warn("Intento de registro de empleado fallido (duplicado u otro): {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e){
            log.error("Error inesperado al registrar empleado: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(Map.of("error", "Ocurrió un error interno al registrar el empleado."));
        }
    }

    @PostMapping("/asesor")
    public ResponseEntity<?> registrarAsesor(@Valid @RequestBody EmpleadoDTO dto) {
         try {
            AsesorEntity asesor = registroService.registrarAsesor(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(asesor);
        } catch (IllegalArgumentException | DataIntegrityViolationException e) {
            log.warn("Intento de registro de asesor fallido (duplicado u otro): {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            log.error("Error inesperado al registrar asesor: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(Map.of("error", "Ocurrió un error interno al registrar el asesor."));
        }
    }

    @PostMapping("/backoffice")
    public ResponseEntity<?> registrarBackOffice(@Valid @RequestBody EmpleadoDTO dto) {
         try {
            BackOfficeEntity backOffice = registroService.registrarBackOffice(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(backOffice);
        } catch (IllegalArgumentException | DataIntegrityViolationException e) {
            log.warn("Intento de registro de backoffice fallido (duplicado u otro): {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            log.error("Error inesperado al registrar backoffice: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(Map.of("error", "Ocurrió un error interno al registrar el backoffice."));
        }
    }
}
package com.backend.bcp.app.Servicio.Infraestructure.adapters.in;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.bcp.app.Servicio.Application.dto.in.CrearServicioDTO;
import com.backend.bcp.app.Servicio.Application.dto.in.EditarServicioDTO;
import com.backend.bcp.app.Servicio.Application.dto.out.ServicioResponseDTO;
import com.backend.bcp.app.Servicio.Application.ports.in.GestionServicioUseCase;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/servicios")
public class ServicioController {
    private final GestionServicioUseCase gestionServicioUseCase;

    public ServicioController(GestionServicioUseCase gestionServicioUseCase) {
        this.gestionServicioUseCase = gestionServicioUseCase;
    }
    @PostMapping
    public ResponseEntity<ServicioResponseDTO> crearServicio(@Valid @RequestBody CrearServicioDTO crearDto) {
        ServicioResponseDTO nuevoServicio = gestionServicioUseCase.crearServicio(crearDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoServicio);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> editarServicio(@PathVariable Long id, @Valid @RequestBody EditarServicioDTO editarDto) {
        try {
            ServicioResponseDTO servicioActualizado = gestionServicioUseCase.editarServicio(id, editarDto);
            return ResponseEntity.ok(servicioActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarServicio(@PathVariable Long id) {
        try {
            gestionServicioUseCase.eliminarServicio(id);
            return ResponseEntity.ok(Map.of("mensaje", "Servicio eliminado correctamente"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerServicioPorId(@PathVariable Long id) {
         try {
            ServicioResponseDTO servicio = gestionServicioUseCase.obtenerServicio(id);
            return ResponseEntity.ok(servicio);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }
}

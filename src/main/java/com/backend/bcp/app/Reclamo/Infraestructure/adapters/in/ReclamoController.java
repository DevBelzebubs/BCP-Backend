package com.backend.bcp.app.Reclamo.Infraestructure.adapters.in;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import com.backend.bcp.app.Reclamo.Application.dto.in.CrearReclamoRequestDTO;
import com.backend.bcp.app.Reclamo.Application.dto.out.ReclamoResponseDTO;
import com.backend.bcp.app.Reclamo.Application.ports.in.GestionReclamosUseCase;
import com.backend.bcp.app.Shared.Infraestructure.config.ApiResponse;

import jakarta.validation.Valid;

//WORKS!
@RestController
@RequestMapping("/api/reclamos")
public class ReclamoController {
    private final GestionReclamosUseCase gestionReclamosUseCase;

    public ReclamoController(GestionReclamosUseCase gestionReclamosUseCase) {
        this.gestionReclamosUseCase = gestionReclamosUseCase;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ReclamoResponseDTO>> crearReclamo(
            @Valid @RequestBody CrearReclamoRequestDTO requestDTO) {
        try {
            ReclamoResponseDTO nuevoReclamo = gestionReclamosUseCase.crearReclamo(requestDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Reclamo registrado exitosamente", nuevoReclamo));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error al crear el reclamo: " + e.getMessage(), null));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ReclamoResponseDTO>> obtenerPorId(@PathVariable Long id) {
        ReclamoResponseDTO dto = gestionReclamosUseCase.obtenerReclamoPorId(id);
        if (dto == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error("Reclamo no encontrado", null));
        return ResponseEntity.ok(ApiResponse.success("Reclamo encontrado", dto));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ReclamoResponseDTO>>> obtenerTodos() {
        List<ReclamoResponseDTO> reclamos = gestionReclamosUseCase.obtenerTodosLosReclamos();
        return ResponseEntity.ok(ApiResponse.success("Reclamos listados", reclamos));
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<ApiResponse<List<ReclamoResponseDTO>>> obtenerPorCliente(@PathVariable Long clienteId) {
        List<ReclamoResponseDTO> reclamos = gestionReclamosUseCase.obtenerReclamosPorClienteId(clienteId);
        if (reclamos == null || reclamos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error("No se encontraron reclamos para el cliente", null));
        }
        return ResponseEntity.ok(ApiResponse.success("Reclamos encontrados", reclamos));
    }
}

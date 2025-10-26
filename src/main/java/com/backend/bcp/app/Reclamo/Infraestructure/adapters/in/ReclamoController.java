package com.backend.bcp.app.Reclamo.Infraestructure.adapters.in;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.bcp.app.Reclamo.Application.dto.in.CrearReclamoRequestDTO;
import com.backend.bcp.app.Reclamo.Application.dto.out.ReclamoResponseDTO;
import com.backend.bcp.app.Reclamo.Application.ports.in.GestionReclamosUseCase;

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
    public ResponseEntity<ReclamoResponseDTO> crearReclamo(@Valid @RequestBody CrearReclamoRequestDTO requestDTO) {
        ReclamoResponseDTO nuevoReclamo = gestionReclamosUseCase.crearReclamo(requestDTO);
        return new ResponseEntity<>(nuevoReclamo, HttpStatus.CREATED);
    }
}

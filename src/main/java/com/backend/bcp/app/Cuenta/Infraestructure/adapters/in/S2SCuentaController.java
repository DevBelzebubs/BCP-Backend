package com.backend.bcp.app.Cuenta.Infraestructure.adapters.in;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.bcp.app.Cuenta.Application.dto.in.CuentaDTO;
import com.backend.bcp.app.Cuenta.Application.ports.in.GestionCuentaUseCase;


@RestController
@RequestMapping("/api/s2s/cuentas")
@PreAuthorize("hasRole('PAYFLOW_SERVICE')")
public class S2SCuentaController {
    @Autowired
    private GestionCuentaUseCase gestionCuentaUseCase;
@GetMapping("/cliente/{dni}")
    public ResponseEntity<List<CuentaDTO>> listsCuentasS2S(@PathVariable String dni) {
        try{
            List<CuentaDTO> cuentas = gestionCuentaUseCase.listarCuentasPorUsuario(dni);
            return ResponseEntity.ok(cuentas);
        }catch(RuntimeException e){
            return ResponseEntity.badRequest().body(null);
        }
    }
}
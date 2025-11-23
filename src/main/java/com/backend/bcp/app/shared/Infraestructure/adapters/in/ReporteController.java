package com.backend.bcp.app.shared.Infraestructure.adapters.in;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.bcp.app.Usuario.Application.ports.in.Empleado.GenerarReporteDiarioUseCase;
import com.backend.bcp.app.shared.Infraestructure.config.ApiResponse;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

//WORKS!
@RestController
@RequestMapping("/api/reportes")
public class ReporteController {
    private final GenerarReporteDiarioUseCase generarReporteDiarioUseCase;

    public ReporteController(GenerarReporteDiarioUseCase generarReporteDiarioUseCase) {
        this.generarReporteDiarioUseCase = generarReporteDiarioUseCase;
    }
    @GetMapping(value = "/diario")
    public ResponseEntity<?> generarReporteDiario(@RequestParam(name = "fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        try {
            byte[] pdfBytes = generarReporteDiarioUseCase.generarReporte(fecha);
            
            String filename = "Reporte_Diario_" + fecha.toString() + ".pdf";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("inline", filename); 
            headers.setContentLength(pdfBytes.length);
            
            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ApiResponse.error("Error al generar el reporte diario: " + e.getMessage(), null));
        }
    }
}
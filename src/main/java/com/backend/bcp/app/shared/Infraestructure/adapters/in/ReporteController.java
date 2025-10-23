package com.backend.bcp.app.Shared.Infraestructure.adapters.in;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.bcp.app.Usuario.Application.ports.in.Empleado.GenerarReporteDiarioUseCase;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/reportes")
public class ReporteController {
    private final GenerarReporteDiarioUseCase generarReporteDiarioUseCase;

    public ReporteController(GenerarReporteDiarioUseCase generarReporteDiarioUseCase) {
        this.generarReporteDiarioUseCase = generarReporteDiarioUseCase;
    }
    @GetMapping(value = "/diario", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generarReporteDiario(@RequestParam(name = "fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        try{
            byte[] pdfBytes = generarReporteDiarioUseCase.generarReporte(fecha);
            String filename = "Reporte_Diario_" + fecha.toString() + ".pdf";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("inline", filename); 
            headers.setContentLength(pdfBytes.length);
            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}

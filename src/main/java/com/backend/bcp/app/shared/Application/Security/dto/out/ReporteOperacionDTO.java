package com.backend.bcp.app.shared.Application.Security.dto.out;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class ReporteOperacionDTO {
        private String fecha;
        private String tipoOperacion;
        private String detalle;
        private BigDecimal monto;

        public ReporteOperacionDTO(LocalDateTime fecha, String tipoOperacion, String detalle, BigDecimal monto) {
            this.fecha = fecha.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            this.tipoOperacion = tipoOperacion;
            this.detalle = detalle;
            this.monto = monto;
        }
        
        // Getters públicos (necesarios para JasperReports)
        public String getFecha() { return fecha; }
        public String getTipoOperacion() { return tipoOperacion; }
        public String getDetalle() { return detalle; }
        public BigDecimal getMonto() { return monto; }
    }
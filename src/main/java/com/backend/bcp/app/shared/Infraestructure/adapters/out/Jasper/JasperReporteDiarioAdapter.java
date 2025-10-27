package com.backend.bcp.app.Shared.Infraestructure.adapters.out.Jasper;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.backend.bcp.app.Pago.Application.dto.out.PagoPersistenceDTO;
import com.backend.bcp.app.Pago.Domain.Pago;
import com.backend.bcp.app.Pago.Domain.PagoPrestamo;
import com.backend.bcp.app.Pago.Domain.PagoServicio;
import com.backend.bcp.app.Prestamo.Application.dto.out.PrestamoPersistenceDTO;
import com.backend.bcp.app.Prestamo.Domain.Prestamo;
import com.backend.bcp.app.Shared.Application.Security.dto.out.ReporteOperacionDTO;
import com.backend.bcp.app.Transaccion.Application.dto.out.MovimientoPersistenceDTO;
import com.backend.bcp.app.Transaccion.Domain.Transaccion;
import com.backend.bcp.app.Usuario.Application.ports.out.Empleado.GeneradorReporteDiarioPdf;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Component
public class JasperReporteDiarioAdapter implements GeneradorReporteDiarioPdf {
    private static final String JRXML_TEMPLATE = 
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
        "<jasperReport xmlns=\"http://jasperreports.sourceforge.net/jasperreports\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://jasperreports.sourceforge.net/jasperreports http://jasperreports.sourceforge.net/xsd/jasperreport.xsd\" name=\"ReporteDiario\" pageWidth=\"595\" pageHeight=\"842\" columnWidth=\"555\" leftMargin=\"20\" rightMargin=\"20\" topMargin=\"20\" bottomMargin=\"20\">\n" +
        "    <property name=\"com.jaspersoft.studio.data.defaultdataadapter\" value=\"One Empty Record\"/>\n" +
        "    <parameter name=\"FECHA_REPORTE\" class=\"java.lang.String\"/>\n" +
        "    <queryString><![CDATA[]]></queryString>\n" +
        "    <field name=\"fecha\" class=\"java.lang.String\"/>\n" +
        "    <field name=\"tipoOperacion\" class=\"java.lang.String\"/>\n" +
        "    <field name=\"detalle\" class=\"java.lang.String\"/>\n" +
        "    <field name=\"monto\" class=\"java.math.BigDecimal\"/>\n" +
        "    <title>\n" +
        "        <band height=\"60\" splitType=\"Stretch\">\n" +
        "            <staticText>\n" +
        "                <reportElement x=\"0\" y=\"0\" width=\"555\" height=\"30\"/>\n" +
        "                <textElement textAlignment=\"Center\" verticalAlignment=\"Middle\">\n" +
        "                    <font size=\"18\" isBold=\"true\"/>\n" +
        "                </textElement>\n" +
        "                <text><![CDATA[REPORTE DIARIO DE OPERACIONES]]></text>\n" +
        "            </staticText>\n" +
        "            <textField>\n" +
        "                <reportElement x=\"0\" y=\"30\" width=\"555\" height=\"20\"/>\n" +
        "                <textElement textAlignment=\"Center\" verticalAlignment=\"Middle\">\n" +
        "                    <font size=\"12\"/>\n" +
        "                </textElement>\n" +
        "                <textFieldExpression><![CDATA[\"Fecha: \" + $P{FECHA_REPORTE}]]></textFieldExpression>\n" +
        "            </textField>\n" +
        "        </band>\n" +
        "    </title>\n" +
        "    <columnHeader>\n" +
        "        <band height=\"30\" splitType=\"Stretch\">\n" +
        "            <staticText><reportElement x=\"0\" y=\"0\" width=\"80\" height=\"30\"/><textElement verticalAlignment=\"Middle\"><font isBold=\"true\"/></textElement><text><![CDATA[Hora]]></text></staticText>\n" +
        "            <staticText><reportElement x=\"80\" y=\"0\" width=\"150\" height=\"30\"/><textElement verticalAlignment=\"Middle\"><font isBold=\"true\"/></textElement><text><![CDATA[Tipo de Operación]]></text></staticText>\n" +
        "            <staticText><reportElement x=\"230\" y=\"0\" width=\"225\" height=\"30\"/><textElement verticalAlignment=\"Middle\"><font isBold=\"true\"/></textElement><text><![CDATA[Detalle]]></text></staticText>\n" +
        "            <staticText><reportElement x=\"455\" y=\"0\" width=\"100\" height=\"30\"/><textElement textAlignment=\"Right\" verticalAlignment=\"Middle\"><font isBold=\"true\"/></textElement><text><![CDATA[Monto (S/)]]></text></staticText>\n" +
        "        </band>\n" +
        "    </columnHeader>\n" +
        "    <detail>\n" +
        "        <band height=\"20\" splitType=\"Stretch\">\n" +
        "            <textField><reportElement x=\"0\" y=\"0\" width=\"80\" height=\"20\"/><textFieldExpression><![CDATA[$F{fecha}]]></textFieldExpression></textField>\n" +
        "            <textField><reportElement x=\"80\" y=\"0\" width=\"150\" height=\"20\"/><textFieldExpression><![CDATA[$F{tipoOperacion}]]></textFieldExpression></textField>\n" +
        "            <textField><reportElement x=\"230\" y=\"0\" width=\"225\" height=\"20\"/><textFieldExpression><![CDATA[$F{detalle}]]></textFieldExpression></textField>\n" +
        "            <textField pattern=\"#,##0.00\"><reportElement x=\"455\" y=\"0\" width=\"100\" height=\"20\"/><textElement textAlignment=\"Right\"/><textFieldExpression><![CDATA[$F{monto}]]></textFieldExpression></textField>\n" +
        "        </band>\n" +
        "    </detail>\n" +
        "</jasperReport>";
    @Override
    public byte[] generarPdf(LocalDate fecha, List<MovimientoPersistenceDTO> transacciones, List<PagoPersistenceDTO> pagos, List<PrestamoPersistenceDTO> prestamos) {
        List<ReporteOperacionDTO> operaciones = new ArrayList<>();
        for (MovimientoPersistenceDTO t : transacciones) {
            operaciones.add(new ReporteOperacionDTO(
                t.fecha(), 
                t.tipo().equals("DEPOSITO") ? "Depósito" : "Retiro", 
                "Transacción ID: " + t.id(), 
                t.monto()
            ));
        }
        for (PagoPersistenceDTO p : pagos) {
            String tipo = "Pago";
            String detalle = "Pago ID: " + p.id();
            if ("SERVICIO".equals(p.tipoPago())) {
                tipo = "Pago de Servicio";
                detalle = "Servicio ID: " + p.servicioId();
            } else if ("PRESTAMO".equals(p.tipoPago())) {
                tipo = "Pago de Préstamo";
                detalle = "Préstamo ID: " + p.prestamoId();
            }
            operaciones.add(new ReporteOperacionDTO(
                p.fecha().atStartOfDay(),
                tipo,
                detalle,
                p.monto()
            ));
        }

        for (PrestamoPersistenceDTO pr : prestamos) {
             operaciones.add(new ReporteOperacionDTO(
                pr.fechaInicio().atStartOfDay(),
                "Préstamo Aprobado",
                "Nuevo Préstamo ID: " + pr.id(),
                pr.monto()
            ));
        }
        try (ByteArrayInputStream templateStream = new ByteArrayInputStream(JRXML_TEMPLATE.getBytes())) {
            
            JasperReport jasperReport = JasperCompileManager.compileReport(templateStream);
            
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("FECHA_REPORTE", fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(operaciones);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            return JasperExportManager.exportReportToPdf(jasperPrint);
            
        } catch (Exception e) {
            throw new RuntimeException("Error en el adaptador: Falló la generación del Reporte Diario en PDF.", e);
        }
    }

}

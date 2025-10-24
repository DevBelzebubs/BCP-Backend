package com.backend.bcp.app.Shared.Infraestructure.adapters.out.Jasper;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.backend.bcp.app.Usuario.Application.dto.out.OperacionSupervisionDTO;
import com.backend.bcp.app.Usuario.Application.ports.out.Empleado.GeneradorReporteGlobalPdf;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Component
public class JasperReporteGlobalAdapter implements GeneradorReporteGlobalPdf {
    private static final String JRXML_TEMPLATE =
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
        "<jasperReport xmlns=\"http://jasperreports.sourceforge.net/jasperreports\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://jasperreports.sourceforge.net/jasperreports http://jasperreports.sourceforge.net/xsd/jasperreport.xsd\" name=\"ReporteGlobal\" pageWidth=\"842\" pageHeight=\"595\" orientation=\"Landscape\" columnWidth=\"802\" leftMargin=\"20\" rightMargin=\"20\" topMargin=\"20\" bottomMargin=\"20\">\n" +
        "    <property name=\"com.jaspersoft.studio.data.defaultdataadapter\" value=\"One Empty Record\"/>\n" +
        "    <parameter name=\"FECHA_GENERACION\" class=\"java.lang.String\"/>\n" +
        "    <queryString><![CDATA[]]></queryString>\n" +
        "    <field name=\"fechaHora\" class=\"java.time.LocalDateTime\"/>\n" +
        "    <field name=\"tipoOperacion\" class=\"java.lang.String\"/>\n" +
        "    <field name=\"id\" class=\"java.lang.Long\"/>\n" +
        "    <field name=\"descripcionBreve\" class=\"java.lang.String\"/>\n" +
        "    <field name=\"monto\" class=\"java.math.BigDecimal\"/>\n" +
        "    <field name=\"estadoActual\" class=\"java.lang.String\"/>\n" +
        "    <title>\n" +
        "        <band height=\"50\" splitType=\"Stretch\">\n" +
        "            <staticText><reportElement x=\"0\" y=\"0\" width=\"802\" height=\"30\"/><textElement textAlignment=\"Center\" verticalAlignment=\"Middle\"><font size=\"18\" isBold=\"true\"/></textElement><text><![CDATA[REPORTE GLOBAL DE OPERACIONES]]></text></staticText>\n" +
        "            <textField><reportElement x=\"0\" y=\"30\" width=\"802\" height=\"20\"/><textElement textAlignment=\"Center\" verticalAlignment=\"Middle\"><font size=\"10\"/></textElement><textFieldExpression><![CDATA[\"Generado el: \" + $P{FECHA_GENERACION}]]></textFieldExpression></textField>\n" +
        "        </band>\n" +
        "    </title>\n" +
        "    <columnHeader>\n" +
        "        <band height=\"30\" splitType=\"Stretch\">\n" +
        "            <staticText><reportElement x=\"0\" y=\"0\" width=\"120\" height=\"30\"/><textElement verticalAlignment=\"Middle\"><font isBold=\"true\"/></textElement><text><![CDATA[Fecha y Hora]]></text></staticText>\n" +
        "            <staticText><reportElement x=\"120\" y=\"0\" width=\"100\" height=\"30\"/><textElement verticalAlignment=\"Middle\"><font isBold=\"true\"/></textElement><text><![CDATA[Tipo Op.]]></text></staticText>\n" +
        "            <staticText><reportElement x=\"220\" y=\"0\" width=\"60\" height=\"30\"/><textElement verticalAlignment=\"Middle\"><font isBold=\"true\"/></textElement><text><![CDATA[ID]]></text></staticText>\n" +
        "            <staticText><reportElement x=\"280\" y=\"0\" width=\"300\" height=\"30\"/><textElement verticalAlignment=\"Middle\"><font isBold=\"true\"/></textElement><text><![CDATA[Descripción]]></text></staticText>\n" +
        "            <staticText><reportElement x=\"580\" y=\"0\" width=\"100\" height=\"30\"/><textElement textAlignment=\"Right\" verticalAlignment=\"Middle\"><font isBold=\"true\"/></textElement><text><![CDATA[Monto (S/)]]></text></staticText>\n" +
        "            <staticText><reportElement x=\"680\" y=\"0\" width=\"122\" height=\"30\"/><textElement verticalAlignment=\"Middle\"><font isBold=\"true\"/></textElement><text><![CDATA[Estado/Detalle]]></text></staticText>\n" +
        "        </band>\n" +
        "    </columnHeader>\n" +
        "    <detail>\n" +
        "        <band height=\"20\" splitType=\"Stretch\">\n" +
        "            <textField pattern=\"dd/MM/yyyy HH:mm:ss\"><reportElement x=\"0\" y=\"0\" width=\"120\" height=\"20\"/><textFieldExpression><![CDATA[$F{fechaHora}]]></textFieldExpression></textField>\n" +
        "            <textField><reportElement x=\"120\" y=\"0\" width=\"100\" height=\"20\"/><textFieldExpression><![CDATA[$F{tipoOperacion}]]></textFieldExpression></textField>\n" +
        "            <textField><reportElement x=\"220\" y=\"0\" width=\"60\" height=\"20\"/><textFieldExpression><![CDATA[$F{id}]]></textFieldExpression></textField>\n" +
        "            <textField isStretchWithOverflow=\"true\"><reportElement x=\"280\" y=\"0\" width=\"300\" height=\"20\"/><textFieldExpression><![CDATA[$F{descripcionBreve}]]></textFieldExpression></textField>\n" +
        "            <textField pattern=\"#,##0.00\" isBlankWhenNull=\"true\"><reportElement x=\"580\" y=\"0\" width=\"100\" height=\"20\"/><textElement textAlignment=\"Right\"/><textFieldExpression><![CDATA[$F{monto}]]></textFieldExpression></textField>\n" +
        "            <textField><reportElement x=\"680\" y=\"0\" width=\"122\" height=\"20\"/><textFieldExpression><![CDATA[$F{estadoActual}]]></textFieldExpression></textField>\n" +
        "        </band>\n" +
        "    </detail>\n" +
        "</jasperReport>";

    @Override
    public byte[] generarPdf(List<OperacionSupervisionDTO> operaciones) {
        try(ByteArrayInputStream templateStream = new ByteArrayInputStream(JRXML_TEMPLATE.getBytes())){
            JasperReport jasperReport = JasperCompileManager.compileReport(templateStream);

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("FECHA_GENERACION", LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(operaciones);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            return JasperExportManager.exportReportToPdf(jasperPrint);
        }catch(Exception e){
            throw new RuntimeException("Error al generar el Reporte Global en PDF.", e);
        }
    }

}

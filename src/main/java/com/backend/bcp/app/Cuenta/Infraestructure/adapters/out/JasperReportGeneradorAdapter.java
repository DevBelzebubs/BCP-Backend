package com.backend.bcp.app.Cuenta.Infraestructure.adapters.out;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.backend.bcp.app.Cuenta.Aplication.ports.out.GeneradorEstadoCuentaPdf;
import com.backend.bcp.app.Cuenta.Domain.Cuenta;
import com.backend.bcp.app.Transaccion.Domain.Transaccion;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class JasperReportGeneradorAdapter implements GeneradorEstadoCuentaPdf {
    private static final String JRXML_TEMPLATE = 
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?><jasperReport xmlns=\"http://jasperreports.sourceforge.net/jasperreports\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://jasperreports.sourceforge.net/jasperreports http://jasperreports.sourceforge.net/xsd/jasperreport.xsd\" name=\"EstadoCuenta\" pageWidth=\"595\" pageHeight=\"842\" columnWidth=\"555\" leftMargin=\"20\" rightMargin=\"20\" topMargin=\"20\" bottomMargin=\"20\">\n" +
        "    <property name=\"com.jaspersoft.studio.data.defaultdataadapter\" value=\"One Empty Record\"/>\n" +
        "    <queryString><![CDATA[]]></queryString>\n" +
        "    <field name=\"tipo\" class=\"java.lang.String\"/>\n" +
        "    <field name=\"monto\" class=\"java.math.BigDecimal\"/>\n" +
        "    <field name=\"fecha\" class=\"java.time.LocalDateTime\"/>\n" +
        "    <title><band height=\"80\">\n" +
        "        <staticText><reportElement x=\"0\" y=\"0\" width=\"555\" height=\"30\"/><textElement textAlignment=\"Center\"><font size=\"18\" isBold=\"true\"/></textElement><text><![CDATA[ESTADO DE CUENTA BANCARIA]]></text></staticText>\n" +
        "        <textField><reportElement x=\"0\" y=\"35\" width=\"555\" height=\"20\"/><textFieldExpression><![CDATA[\"Cuenta N°: \" + $P{NUMERO_CUENTA} + \" | Saldo: \" + $P{SALDO_ACTUAL}]]></textFieldExpression></textField>\n" +
        "    </band></title>\n" +
        "    <columnHeader><band height=\"20\">\n" +
        "        <staticText><reportElement x=\"0\" y=\"0\" width=\"185\" height=\"20\"/><text><![CDATA[Fecha]]></text></staticText>" +
        "        <staticText><reportElement x=\"185\" y=\"0\" width=\"185\" height=\"20\"/><text><![CDATA[Tipo]]></text></staticText>" +
        "        <staticText><reportElement x=\"370\" y=\"0\" width=\"185\" height=\"20\"/><text><![CDATA[Monto]]></text></staticText>" +
        "    </band></columnHeader>\n" +
        "    <detail><band height=\"20\">\n" +
        "        <textField><reportElement x=\"0\" y=\"0\" width=\"185\" height=\"20\"/><textFieldExpression><![CDATA[$F{fecha}]]></textFieldExpression></textField>" +
        "        <textField><reportElement x=\"185\" y=\"0\" width=\"185\" height=\"20\"/><textFieldExpression><![CDATA[$F{tipo}]]></textFieldExpression></textField>" +
        "        <textField><reportElement x=\"370\" y=\"0\" width=\"185\" height=\"20\"/><textFieldExpression><![CDATA[$F{monto}]]></textFieldExpression></textField>" +
        "    </band></detail>\n" +
        "</jasperReport>";

    @Override
    public byte[] generarPdf(Cuenta cuenta, List<Transaccion> movimientos) {
        try{
            JasperReport jasperReport = JasperCompileManager.compileReport(JRXML_TEMPLATE);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("NUMERO_CUENTA", cuenta.getNumeroCuenta());
            parameters.put("SALDO_ACTUAL", cuenta.getSaldo());

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(movimientos);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            return JasperExportManager.exportReportToPdf(jasperPrint);
        }catch(JRException e){
            throw new RuntimeException("Error en el adaptador: Falló la generación del Estado de Cuenta en formato PDF.", e);
        }
    }

}

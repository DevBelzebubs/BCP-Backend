package com.backend.bcp.app.Pago.Aplication.persistence;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.backend.bcp.app.Comprobante.Aplication.dto.ComprobanteDTO;
import com.backend.bcp.app.Comprobante.Aplication.ports.out.ComprobanteRepository;
import com.backend.bcp.app.Comprobante.Domain.Comprobante;
import com.backend.bcp.app.Cuenta.Aplication.dto.in.CuentaPersistenceDTO;
import com.backend.bcp.app.Cuenta.Aplication.ports.out.CuentaRepository;
import com.backend.bcp.app.Cuenta.Domain.Cuenta;
import com.backend.bcp.app.Pago.Aplication.dto.in.PagoPendienteDTO;
import com.backend.bcp.app.Pago.Aplication.ports.in.RealizarPagoUseCase;
import com.backend.bcp.app.Pago.Aplication.ports.out.PagoRepository;
import com.backend.bcp.app.Servicio.Aplication.dto.in.ServicioPersistenceDTO;
import com.backend.bcp.app.Servicio.Aplication.ports.out.ServicioRepository;
import com.backend.bcp.app.Servicio.Domain.Servicio;
import com.backend.bcp.app.Usuario.Domain.Cliente;

@Service
public class RealizarPagoService implements RealizarPagoUseCase {
private final PagoRepository pagoRepository;
    private final CuentaRepository cuentaRepository;
    private final ServicioRepository servicioRepository;
    private final ComprobanteRepository comprobanteRepository;

    private final static Comprobante mockComprobanteDomain = new Comprobante(); 
    public RealizarPagoService(PagoRepository pagoRepository, CuentaRepository cuentaRepository,
            ServicioRepository servicioRepository, ComprobanteRepository comprobanteRepository) {
        this.pagoRepository = pagoRepository;
        this.cuentaRepository = cuentaRepository;
        this.servicioRepository = servicioRepository;
        this.comprobanteRepository = comprobanteRepository;
    }
    @Override
    public List<PagoPendienteDTO> listarPagosPendientes(Long usuarioId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listarPagosPendientes'");
    }
    @Override
    public ComprobanteDTO realizarPago(Long cuentaId, Long servicioId, BigDecimal monto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'realizarPago'");
    }
    

}

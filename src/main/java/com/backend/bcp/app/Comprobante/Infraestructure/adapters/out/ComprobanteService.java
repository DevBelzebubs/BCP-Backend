package com.backend.bcp.app.Comprobante.Infraestructure.adapters.out;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.backend.bcp.app.Comprobante.Application.dto.ComprobanteDTO;
import com.backend.bcp.app.Comprobante.Application.ports.out.ComprobanteRepository;
import com.backend.bcp.app.Comprobante.Domain.Comprobante;
import com.backend.bcp.app.Comprobante.Infraestructure.entity.ComprobanteEntity;
import com.backend.bcp.app.Comprobante.Infraestructure.repo.SpringDataComprobanteRepository;

@Service
public class ComprobanteService implements ComprobanteRepository {
    SpringDataComprobanteRepository springDataComprobanteRepository;
    ComprobanteMapper mapper;
    
    public ComprobanteService(SpringDataComprobanteRepository springDataComprobanteRepository,
            ComprobanteMapper mapper) {
        this.springDataComprobanteRepository = springDataComprobanteRepository;
        this.mapper = mapper;
    }
    

    @Override
    public Comprobante guardarComprobante(Comprobante comprobante) {
        ComprobanteEntity entity = mapper.toEntity(comprobante);
        ComprobanteEntity savedEntity = springDataComprobanteRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }


    @Override
    public ComprobanteDTO generarComprobante(String detalle, BigDecimal monto, String codigoAutorizacion) {
        Comprobante comprobanteDomain = new Comprobante();
        comprobanteDomain.setServicio(detalle);
        comprobanteDomain.setMontoPagado(monto);
        comprobanteDomain.setFecha(LocalDate.now());
        comprobanteDomain.setCodigoAutorizacion(codigoAutorizacion);

        Comprobante comprobanteGuardado = guardarComprobante(comprobanteDomain);

        return new ComprobanteDTO(
                comprobanteGuardado.getId(),
                comprobanteGuardado.getServicio(),
                comprobanteGuardado.getMontoPagado(),
                comprobanteGuardado.getFecha(),
                comprobanteGuardado.getCodigoAutorizacion()
        );
    }

}

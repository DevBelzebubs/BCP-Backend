package com.backend.bcp.app.Cuenta.Infraestructure.adapters.out;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.backend.bcp.app.Cuenta.Aplication.dto.out.CuentaMapper;
import com.backend.bcp.app.Cuenta.Aplication.ports.out.CuentaRepository;
import com.backend.bcp.app.Cuenta.Domain.Cuenta;
import com.backend.bcp.app.Cuenta.Infraestructure.entity.CuentaEntity;
import com.backend.bcp.app.Cuenta.Infraestructure.repo.SpringDataCuentaRepository;

import org.springframework.transaction.annotation.Transactional;

@Component
public class JpaCuentaRepositoryAdapter implements CuentaRepository {
    private final SpringDataCuentaRepository cuentaRepository;
    private final CuentaMapper mapper;
    public JpaCuentaRepositoryAdapter(SpringDataCuentaRepository cuentaRepository, CuentaMapper mapper) {
        this.cuentaRepository = cuentaRepository;
        this.mapper = mapper;
    }
    @Override
    @Transactional(readOnly = true)
    public List<Cuenta> obtenerCuentasPorUsuario(Long usuarioId) {
        List<CuentaEntity> entities = cuentaRepository.findByClienteId_UsuarioId(usuarioId);
        return entities.stream().map(mapper::toDomain).collect(Collectors.toList());
    }
    @Override
    @Transactional(readOnly = true)
    public Optional<Cuenta> obtenerPorId(Long cuentaId) {
        return cuentaRepository.findById(cuentaId).map(mapper::toDomain);
    }
    @Override
    @Transactional(readOnly = true)
    public void actualizar(Cuenta cuenta) {
        CuentaEntity cuentaEntity = cuentaRepository.findById(cuenta.getId()).orElseThrow(()-> new RuntimeException("Cuenta no encontrada para actualizar"));
        cuentaEntity.setTipoCuenta(cuenta.getTipo());
        cuentaEntity.setEstadoCuenta(cuenta.getEstadoCuenta());
        cuentaEntity.setNumeroCuenta(cuenta.getNumeroCuenta());
        cuentaEntity.setSaldo(cuenta.getSaldo());
        cuentaRepository.save(cuentaEntity);
    }
    
}

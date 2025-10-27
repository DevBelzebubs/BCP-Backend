package com.backend.bcp.app.Shared.Infraestructure.adapters.out;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.backend.bcp.app.Shared.Application.Security.dto.in.ClienteDTO;
import com.backend.bcp.app.Shared.Application.Security.dto.in.EmpleadoDTO;
import com.backend.bcp.app.Shared.Application.Security.ports.in.RegistroService;
import com.backend.bcp.app.Shared.Infraestructure.entity.UsuarioEntity;
import com.backend.bcp.app.Shared.Infraestructure.repo.SpringDataUserRepository;
import com.backend.bcp.app.Usuario.Infraestructure.entity.cliente.ClienteEntity;
import com.backend.bcp.app.Usuario.Infraestructure.entity.empleado.AsesorEntity;
import com.backend.bcp.app.Usuario.Infraestructure.entity.empleado.BackOfficeEntity;
import com.backend.bcp.app.Usuario.Infraestructure.entity.empleado.EmpleadoEntity;
import com.backend.bcp.app.Usuario.Infraestructure.repo.Cliente.SpringDataClientRepository;
import com.backend.bcp.app.Usuario.Infraestructure.repo.Empleado.SpringADataBackofficeRepository;
import com.backend.bcp.app.Usuario.Infraestructure.repo.Empleado.SpringDataAsesorRepository;
import com.backend.bcp.app.Usuario.Infraestructure.repo.Empleado.SpringDataEmpleadoRepository;

import jakarta.transaction.Transactional;

@Service
public class RegistroServiceImpl implements RegistroService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    private final SpringDataUserRepository springDataUserRepository;
    private final SpringDataClientRepository springDataClientRepository;
    private final SpringDataEmpleadoRepository springDataEmpleadoRepository;
    private final SpringDataAsesorRepository springDataAsesorRepository;
    private final SpringADataBackofficeRepository springADataBackofficeRepository;
    public RegistroServiceImpl(SpringDataUserRepository springDataUserRepository,
            SpringDataClientRepository springDataClientRepository,
            SpringDataEmpleadoRepository springDataEmpleadoRepository, SpringDataAsesorRepository springDataAsesorRepository, SpringADataBackofficeRepository springADataBackofficeRepository) {
        this.springDataUserRepository = springDataUserRepository;
        this.springDataClientRepository = springDataClientRepository; 
        this.springDataEmpleadoRepository = springDataEmpleadoRepository;
        this.springDataAsesorRepository = springDataAsesorRepository;
        this.springADataBackofficeRepository = springADataBackofficeRepository;
    }
    private void validarUsuarioDuplicado(String nombre, String correo, String dni) {
        if (springDataUserRepository.findByNombre(nombre).isPresent()) {
            throw new IllegalArgumentException("El nombre de usuario '" + nombre + "' ya está en uso.");
        }
        if (springDataUserRepository.findByCorreo(correo).isPresent()) {
            throw new IllegalArgumentException("El correo electrónico '" + correo + "' ya está registrado.");
        }
        if (springDataUserRepository.findByDni(dni).isPresent()) {
            throw new IllegalArgumentException("El DNI '" + dni + "' ya está registrado.");
        }
    }
    @Override
    public ClienteEntity registrarCliente(ClienteDTO dto) {
        validarUsuarioDuplicado(dto.getNombre(), dto.getCorreo(), dto.getDni());
        String passwordEncrypt = passwordEncoder.encode(dto.getContrasena());
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setNombre(dto.getNombre());
        usuario.setContrasena(passwordEncrypt);
        usuario.setCorreo(dto.getCorreo());
        usuario.setDni(dto.getDni());
        usuario.setDireccion(dto.getDireccion());
        usuario.setTelefono(dto.getTelefono());

        ClienteEntity cliente = new ClienteEntity();
        cliente.setFechaRegistro(LocalDate.now());
        cliente.setIdUsuario(usuario);

        try {
            return springDataClientRepository.save(cliente);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Error al registrar: el nombre, correo o DNI ya existe (detectado por BD).");
        }
    }
    public EmpleadoEntity crearEmpleado(EmpleadoDTO dto){
        validarUsuarioDuplicado(dto.getNombre(), dto.getCorreo(), dto.getDni());
        String passwordEncrypt = passwordEncoder.encode(dto.getContrasena());
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setNombre(dto.getNombre());
        usuario.setContrasena(passwordEncrypt);
        usuario.setCorreo(dto.getCorreo());
        usuario.setDni(dto.getDni());
        usuario.setDireccion(dto.getDireccion());
        usuario.setTelefono(dto.getTelefono());


        EmpleadoEntity empleado = new EmpleadoEntity();
        empleado.setFechaContratacion(LocalDate.now());
        empleado.setSalario(dto.getSalario());
        empleado.setIdUsuario(usuario);
        return empleado;
    }
    @Override
    public EmpleadoEntity registrarEmpleado(EmpleadoDTO dto) {
        EmpleadoEntity empleadoBase = crearEmpleado(dto);
        try {
             return springDataEmpleadoRepository.save(empleadoBase);
        } catch (DataIntegrityViolationException e) {
             throw new IllegalArgumentException("Error al registrar empleado: el nombre, correo o DNI ya existe (detectado por BD).");
        }
    }

    @Override
    @Transactional
    public AsesorEntity registrarAsesor(EmpleadoDTO dto) {
        EmpleadoEntity empleadoBase = crearEmpleado(dto);
        EmpleadoEntity empleadoGuardado = springDataEmpleadoRepository.save(empleadoBase);
        AsesorEntity asesor = new AsesorEntity();
        asesor.setIdEmpleado(empleadoGuardado);
        try {
            return springDataAsesorRepository.save(asesor);
        } catch (DataIntegrityViolationException e) {
             throw new IllegalArgumentException("Error al registrar asesor: el nombre, correo o DNI ya existe (detectado por BD).");
        }
    }

    @Override
    public BackOfficeEntity registrarBackOffice(EmpleadoDTO dto) {
        EmpleadoEntity empleadoBase = crearEmpleado(dto);
        EmpleadoEntity empleadoGuardado = springDataEmpleadoRepository.save(empleadoBase);
        BackOfficeEntity backOffice = new BackOfficeEntity();
        backOffice.setIdEmpleado(empleadoGuardado);
        try {
             return springADataBackofficeRepository.save(backOffice);
        } catch (DataIntegrityViolationException e) {
             throw new IllegalArgumentException("Error al registrar backoffice: el nombre, correo o DNI ya existe (detectado por BD).");
        }
    }

}

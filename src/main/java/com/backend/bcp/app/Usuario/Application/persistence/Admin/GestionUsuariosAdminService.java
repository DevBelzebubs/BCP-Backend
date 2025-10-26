package com.backend.bcp.app.Usuario.Application.persistence.Admin;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.bcp.app.Shared.Application.dto.in.CambiarRolRequestDTO;
import com.backend.bcp.app.Shared.Application.dto.out.EmpleadoRolDTO;
import com.backend.bcp.app.Shared.Infraestructure.entity.UsuarioEntity;
import com.backend.bcp.app.Shared.Infraestructure.entity.enums.TipoRol;
import com.backend.bcp.app.Usuario.Application.ports.in.Admin.GestionUsuariosAdminUseCase;
import com.backend.bcp.app.Usuario.Application.ports.out.Admin.AuditoriaRolPort;
import com.backend.bcp.app.Usuario.Infraestructure.entity.empleado.AsesorEntity;
import com.backend.bcp.app.Usuario.Infraestructure.entity.empleado.BackOfficeEntity;
import com.backend.bcp.app.Usuario.Infraestructure.entity.empleado.EmpleadoEntity;
import com.backend.bcp.app.Usuario.Infraestructure.repo.Empleado.SpringADataBackofficeRepository;
import com.backend.bcp.app.Usuario.Infraestructure.repo.Empleado.SpringDataAdminRepository;
import com.backend.bcp.app.Usuario.Infraestructure.repo.Empleado.SpringDataAsesorRepository;
import com.backend.bcp.app.Usuario.Infraestructure.repo.Empleado.SpringDataEmpleadoRepository;

@Service
public class GestionUsuariosAdminService implements GestionUsuariosAdminUseCase {
    private final SpringDataEmpleadoRepository empleadoRepository;
    private final SpringDataAsesorRepository asesorRepository;
    private final SpringADataBackofficeRepository backofficeRepository;
    private final SpringDataAdminRepository adminRepository;
    private final AuditoriaRolPort auditoriaRolPort;

    public GestionUsuariosAdminService(SpringDataEmpleadoRepository empleadoRepository,
            SpringDataAsesorRepository asesorRepository, SpringADataBackofficeRepository backofficeRepository,
            SpringDataAdminRepository adminRepository, AuditoriaRolPort auditoriaRolPort) {
        this.empleadoRepository = empleadoRepository;
        this.asesorRepository = asesorRepository;
        this.backofficeRepository = backofficeRepository;
        this.adminRepository = adminRepository;
        this.auditoriaRolPort = auditoriaRolPort;
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmpleadoRolDTO> listarEmpleadosConRoles() {
        List<EmpleadoEntity> empleados = empleadoRepository.findAll();
        List<EmpleadoRolDTO> resultado = new ArrayList<>();
        for (EmpleadoEntity emp : empleados) {
            TipoRol rol = determinarRolActual(emp.getIdEmpleado());
            UsuarioEntity usr = emp.getIdUsuario();
            resultado.add(new EmpleadoRolDTO(
                    emp.getIdEmpleado(),
                    usr.getId(),
                    usr.getNombre(),
                    usr.getCorreo(),
                    rol
            ));
        }
        return resultado;
    }

    @Override
    @Transactional
    public EmpleadoRolDTO cambiarRolEmpleado(Long empleadoId, CambiarRolRequestDTO request, Long adminUserId) {
        EmpleadoEntity empleado = empleadoRepository.findById(empleadoId)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado con ID: " + empleadoId));
        TipoRol rolActual = determinarRolActual(empleadoId);
        TipoRol rolNuevo = request.nuevoRol();
        if (rolActual.equals(rolNuevo)) {
            throw new RuntimeException("El empleado ya tiene el rol " + rolNuevo);
        }
        if (TipoRol.ADMIN.equals(rolActual)) {
            throw new RuntimeException("No se puede cambiar el rol de un Administrador.");
        }
        if (TipoRol.ADMIN.equals(rolNuevo) || TipoRol.EMPLEADO.equals(rolNuevo)) {
             throw new RuntimeException("No se puede asignar el rol " + rolNuevo + " directamente.");
        }

        LocalDateTime fechaHora = LocalDateTime.now();

        if (TipoRol.ASESOR.equals(rolActual)) {
            asesorRepository.findByIdEmpleado_IdEmpleado(empleadoId).ifPresent(asesorRepository::delete);
        } else if (TipoRol.BACKOFFICE.equals(rolActual)) {
            backofficeRepository.findByIdEmpleado_IdEmpleado(empleadoId).ifPresent(backofficeRepository::delete);
        }

        auditoriaRolPort.guardarAuditoria(adminUserId, empleadoId, rolActual, rolNuevo, fechaHora);

        if (TipoRol.ASESOR.equals(rolNuevo)) {
            AsesorEntity asesor = new AsesorEntity();
            asesor.setIdEmpleado(empleado);
            asesorRepository.save(asesor);
        } else if (TipoRol.BACKOFFICE.equals(rolNuevo)) {
            BackOfficeEntity backOffice = new BackOfficeEntity();
            backOffice.setIdEmpleado(empleado);
            backofficeRepository.save(backOffice);
        }
        UsuarioEntity usr = empleado.getIdUsuario();
        return new EmpleadoRolDTO(
                empleadoId,
                usr.getId(),
                usr.getNombre(),
                usr.getCorreo(),
                rolNuevo
        );
    }
    private TipoRol determinarRolActual(Long empleadoId) {
        if (adminRepository.findByIdEmpleado_IdEmpleado(empleadoId).isPresent()) {
            return TipoRol.ADMIN;
        } else if (asesorRepository.findByIdEmpleado_IdEmpleado(empleadoId).isPresent()) {
            return TipoRol.ASESOR;
        } else if (backofficeRepository.findByIdEmpleado_IdEmpleado(empleadoId).isPresent()) {
            return TipoRol.BACKOFFICE;
        } else {
            return TipoRol.EMPLEADO;
        }
    }
}

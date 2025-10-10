package com.backend.bcp.shared.Aplication.Security.dto.out;

import org.springframework.stereotype.Component;

import com.backend.bcp.app.Usuario.Domain.Admin;
import com.backend.bcp.app.Usuario.Domain.Asesor;
import com.backend.bcp.app.Usuario.Domain.BackOffice;
import com.backend.bcp.app.Usuario.Domain.Cliente;
import com.backend.bcp.app.Usuario.Infraestructure.entity.cliente.ClienteEntity;
import com.backend.bcp.app.Usuario.Infraestructure.entity.empleado.AdminEntity;
import com.backend.bcp.app.Usuario.Infraestructure.entity.empleado.AsesorEntity;
import com.backend.bcp.app.Usuario.Infraestructure.entity.empleado.BackOfficeEntity;
import com.backend.bcp.app.Usuario.Infraestructure.entity.empleado.EmpleadoEntity;
import com.backend.bcp.shared.Domain.Usuario;
import com.backend.bcp.shared.Infraestructure.entity.UsuarioEntity;

@Component
public class UsuarioMapper {
    public Usuario toDomain(UsuarioEntity entity) {
        Usuario usuario = new Usuario();
        usuario.setId(entity.getId());
        usuario.setNombre(entity.getNombre());
        usuario.setCorreo(entity.getCorreo());
        usuario.setContrasena(entity.getContrasena());
        return usuario;
    }
    public UsuarioEntity toEntity(Usuario domain) {
        UsuarioEntity entity = new UsuarioEntity();
        entity.setId(domain.getId());
        entity.setNombre(domain.getNombre());
        entity.setCorreo(domain.getCorreo());
        entity.setContrasena(domain.getContrasena());
        return entity;
    }

    public static Cliente toDomain(ClienteEntity entity) {
        if (entity == null) return null;
        UsuarioEntity u = entity.getIdUsuario();
        return new Cliente(
            u.getId(), u.getNombre(), u.getContrasena(),
            u.getCorreo(), u.getDni(), u.getDireccion(), u.getTelefono(),
            entity.getFechaRegistro()
        );
    }
    public static ClienteEntity toEntity(Cliente domain) {
        UsuarioEntity usuario = baseToEntity(domain);
        ClienteEntity e = new ClienteEntity();
        e.setIdCliente(domain.getId());
        e.setFechaRegistro(domain.getFechaRegistro());
        e.setIdUsuario(usuario);
        return e;
    }

     public static Asesor toDomain(AsesorEntity entity) {
        if (entity == null) return null;

        EmpleadoEntity emp = entity.getIdEmpleado();
        UsuarioEntity u = emp.getIdUsuario();
        return new Asesor(u.getId(), u.getNombre(),u.getContrasena(),u.getCorreo(),u.getDni(),u.getDireccion(),u.getTelefono(),emp.getFechaContratacion(),emp.getSalario());
    }
    public static AsesorEntity toEntity(Asesor domain) {
        if (domain == null) return null;
        UsuarioEntity u = new UsuarioEntity();
        u.setId(domain.getId());
        u.setNombre(domain.getNombre());
        u.setContrasena(domain.getContrasena());
        u.setCorreo(domain.getCorreo());
        u.setDni(domain.getDni());
        u.setDireccion(domain.getDireccion());
        u.setTelefono(domain.getTelefono());

        EmpleadoEntity emp = new EmpleadoEntity();
        emp.setFechaContratacion(domain.getFechaContratacion());
        emp.setSalario(domain.getPago());
        emp.setIdUsuario(u);

        AsesorEntity a = new AsesorEntity();
        a.setIdEmpleado(emp);
        return a;
    }
    public static BackOffice toDomain(BackOfficeEntity entity){
        if(entity == null) return null;
        EmpleadoEntity bOffice = entity.getIdEmpleado();
        UsuarioEntity u = bOffice.getIdUsuario();
        return new BackOffice(u.getId(), u.getNombre(), u.getContrasena(), u.getCorreo(), u.getDni(), u.getDireccion(), u.getTelefono(), bOffice.getSalario(), bOffice.getFechaContratacion());
    }

    public static BackOfficeEntity toEntity(BackOffice domain){
        if (domain == null) return null;
        UsuarioEntity u = new UsuarioEntity();
        u.setId(domain.getId());
        u.setNombre(domain.getNombre());
        u.setContrasena(domain.getContrasena());
        u.setCorreo(domain.getCorreo());
        u.setDni(domain.getDni());
        u.setDireccion(domain.getDireccion());
        u.setTelefono(domain.getTelefono());

        EmpleadoEntity emp = new EmpleadoEntity();
        emp.setFechaContratacion(domain.getFechaContratacion());
        emp.setSalario(domain.getPago());
        emp.setIdUsuario(u);
        BackOfficeEntity bOffice = new BackOfficeEntity();
        bOffice.setIdEmpleado(emp);
        return bOffice;
    }
    public static Admin toDomain(AdminEntity entity){
        if(entity == null) return null;
        EmpleadoEntity adminEmp = entity.getIdEmpleado();
        UsuarioEntity u = adminEmp.getIdUsuario();
        return new Admin(u.getId(), u.getNombre(), u.getContrasena(), u.getCorreo(), u.getDni(), u.getDireccion(), u.getTelefono(), adminEmp.getSalario(), adminEmp.getFechaContratacion());
    }

    public static AdminEntity toEntity(Admin domain){
        if (domain == null) return null;
        UsuarioEntity u = new UsuarioEntity();
        u.setId(domain.getId());
        u.setNombre(domain.getNombre());
        u.setContrasena(domain.getContrasena());
        u.setCorreo(domain.getCorreo());
        u.setDni(domain.getDni());
        u.setDireccion(domain.getDireccion());
        u.setTelefono(domain.getTelefono());

        EmpleadoEntity emp = new EmpleadoEntity();
        emp.setFechaContratacion(domain.getFechaContratacion());
        emp.setSalario(domain.getPago());
        emp.setIdUsuario(u);
        
        AdminEntity admin = new AdminEntity();
        admin.setIdEmpleado(emp);
        return admin;
    }
    private static UsuarioEntity baseToEntity(Usuario usuario) {
        if (usuario == null) return null;
        UsuarioEntity e = new UsuarioEntity();
        e.setId(usuario.getId());
        e.setNombre(usuario.getNombre());
        e.setContrasena(usuario.getContrasena());
        e.setCorreo(usuario.getCorreo());
        e.setDni(usuario.getDni());
        e.setDireccion(usuario.getDireccion());
        e.setTelefono(usuario.getTelefono());
        return e;
    }
}
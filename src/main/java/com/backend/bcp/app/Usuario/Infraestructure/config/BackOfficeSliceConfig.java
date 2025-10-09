package com.backend.bcp.app.Usuario.Infraestructure.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
    "com.backend.bcp.app.Usuario.Aplicacion.backoffice",
    "com.backend.bcp.app.Usuario.Infraestructure.backoffice"
})
@EntityScan(basePackages = {
    "com.backend.bcp.app.Usuario.Infraestructure.entity.empleado", // Contiene EmpleadoEntity y BackOfficeEntity
    "com.backend.bcp.shared.Infraestructure.entity" // Contiene UsuarioEntity
})
public class BackOfficeSliceConfig {

}

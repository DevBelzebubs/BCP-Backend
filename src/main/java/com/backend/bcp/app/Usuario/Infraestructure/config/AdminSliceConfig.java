package com.backend.bcp.app.Usuario.Infraestructure.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
    "com.backend.bcp.app.Usuario.Aplicacion.admin",
    "com.backend.bcp.app.Usuario.Infraestructure.admin"
})
@EntityScan(basePackages = {
    "com.backend.bcp.app.Usuario.Infraestructure.entity.empleado",
    "com.backend.bcp.shared.Infraestructure.entity"
})
public class AdminSliceConfig {

}

package com.backend.bcp.app.Usuario.Infraestructure.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackages = {
    "com.backend.bcp.app.Usuario.Aplicacion.asesor",
    "com.backend.bcp.app.Usuario.Infraestructure.asesor"
})
@EnableJpaRepositories(basePackages = "com.bcp.app.Usuario.Infraestructure.repo.asesor")
@EntityScan(basePackages = {
    "com.backend.bcp.app.Usuario.Infraestructure.entity.empleado",
    "com.backend.bcp.app.Usuario.Infraestructure.entity.asesor"
})
public class AsesorSliceConfig {
}
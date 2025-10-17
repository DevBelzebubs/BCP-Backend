package com.backend.bcp.app.Usuario.Infraestructure.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
    "com.backend.bcp.app.Usuario.Aplicacion.admin",
    "com.backend.bcp.app.Usuario.Infraestructure.admin"
})

public class AdminSliceConfig {

}

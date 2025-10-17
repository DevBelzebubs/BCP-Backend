package com.backend.bcp.app.Usuario.Infraestructure.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
    "com.backend.bcp.app.Usuario.Aplicacion.cliente",
    "com.backend.bcp.app.Usuario.Infraestructure.cliente"
})
public class ClienteSliceConfig {

}

package com.backend.bcp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {
    "com.backend.bcp.shared.Infraestructure.repo",
    "com.backend.bcp.app.Usuario.Infraestructure.repo",
    "com.backend.bcp.app.Cuenta.Infraestructure.repo",
    "com.backend.bcp.app.Transaccion.Infraestructure.repo",
    "com.backend.bcp.app.Comprobante.Infraestructure.repo",
    "com.backend.bcp.app.Pago.Infraestructure.repo",
    "com.backend.bcp.app.Servicio.Infraestructure.repo"
})
@EntityScan(basePackages = {
    "com.backend.bcp.shared.Infraestructure.entity",
    "com.backend.bcp.app.Usuario.Infraestructure.entity",
    "com.backend.bcp.app.Cuenta.Infraestructure.entity",
    "com.backend.bcp.app.Transaccion.Infraestructure.entity",
    "com.backend.bcp.app.Comprobante.Infraestructure.entity",
    "com.backend.bcp.app.Pago.Infraestructure.entity",
    "com.backend.bcp.app.Servicio.Infraestructure.entity"
})
@ComponentScan(basePackages = "com.backend.bcp")
public class BancoDeCreditoApplication {
	public static void main(String[] args) {
		SpringApplication.run(BancoDeCreditoApplication.class, args);
	}
	@Bean
	CommandLineRunner testContext(ApplicationContext context) {
    return args -> {
        System.out.println("Beans encontrados:");
        for (String beanName : context.getBeanDefinitionNames()) {
            if (beanName.toLowerCase().contains("user")) {
                System.out.println(" -> " + beanName);
            }
        }
    };
}
}

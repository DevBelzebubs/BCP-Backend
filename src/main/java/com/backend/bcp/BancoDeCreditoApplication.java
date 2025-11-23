package com.backend.bcp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.backend.bcp.app.shared.Infraestructure.Security.JwtConfig;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {
    "com.backend.bcp.app.Shared.Infraestructure.repo",
    "com.backend.bcp.app.Usuario.Infraestructure.repo",
    "com.backend.bcp.app.Cuenta.Infraestructure.repo",
    "com.backend.bcp.app.Transaccion.Infraestructure.repo",
    "com.backend.bcp.app.Comprobante.Infraestructure.repo",
    "com.backend.bcp.app.Pago.Infraestructure.repo",
    "com.backend.bcp.app.Servicio.Infraestructure.repo",
    "com.backend.bcp.app.Prestamo.Infraestructure.repo",
    "com.backend.bcp.app.Reclamo.Infraestructure.repo"
})
@EntityScan(basePackages = {
    "com.backend.bcp.app", 
    "com.backend.bcp.shared" 
})
@ComponentScan(basePackages = "com.backend.bcp")
public class BancoDeCreditoApplication {
    private final JwtConfig jwtConfig;
    
	public BancoDeCreditoApplication(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }
    
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

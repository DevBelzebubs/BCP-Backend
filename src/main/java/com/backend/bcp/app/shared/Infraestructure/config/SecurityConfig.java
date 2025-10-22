package com.backend.bcp.app.shared.Infraestructure.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.backend.bcp.app.shared.Infraestructure.Security.JwtFilter;
import com.backend.bcp.app.shared.Infraestructure.adapters.out.JwtService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final JwtService tokenService;
    public SecurityConfig(JwtService tokenService) {
        this.tokenService = tokenService;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/auth/**","/api/registro/**").permitAll()
            
            .requestMatchers("/api/admin/**").hasRole("ADMIN")

            .requestMatchers("/api/cuentas/**", "/api/pagos/**", "/api/transferencias/**", "/api/cliente/**").hasRole("CLIENTE")

            // --- INICIO DE LA CORRECCIÓN ---
            // 1. REGLA ESPECÍFICA: El CLIENTE SÍ puede solicitar
            .requestMatchers("/api/prestamos/solicitar").hasRole("CLIENTE")
            
            // 2. REGLA GENERAL: Los demás endpoints de prestamos son para Asesores/Admins
            .requestMatchers("/api/prestamos/**", "/api/asesor/**").hasAnyRole("ASESOR", "BACKOFFICE","ADMIN")
            // --- FIN DE LA CORRECCIÓN ---

            .requestMatchers("/api/backoffice/**", "/api/reclamos/**", "/api/empleados/**").hasAnyRole("BACKOFFICE","ADMIN")

            .anyRequest().authenticated()
            )
            .addFilterBefore(new JwtFilter(tokenService), UsernamePasswordAuthenticationFilter.class)
            .cors(cors -> cors.configurationSource(corsConfiguration()));
        return httpSecurity.build();
    }

    @Bean
    public CorsConfigurationSource corsConfiguration(){
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:4200"));
        config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        config.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",config);
        return source;
    }

}
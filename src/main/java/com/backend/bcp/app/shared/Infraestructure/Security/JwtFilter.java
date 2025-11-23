package com.backend.bcp.app.shared.Infraestructure.Security;

import java.io.IOException;
import java.util.Collections;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.backend.bcp.app.shared.Application.Security.ports.out.TokenService;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private final TokenService tokenService;
    
    public JwtFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(JwtConfig.HEADER_AUTHORIZATION);
        if (header != null && header.startsWith(JwtConfig.PREFIX)) {
            String token = header.replace(JwtConfig.PREFIX, "").trim();
            try {
                Claims claims = tokenService.getClaims(token);
                String username = claims.getSubject();
                String tipoUsuario = claims.get("tipoUsuario", String.class); 

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + tipoUsuario.toUpperCase()); 
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        username, 
                        null, 
                        Collections.singletonList(authority)
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            } catch (Exception e) {
                // --- AÑADE ESTAS LÍNEAS PARA DIAGNÓSTICO ---
                System.err.println("Error al procesar el token JWT: " + e.getClass().getName() + " - " + e.getMessage());
                // La siguiente línea te dará el detalle completo del error
                e.printStackTrace(); 
                // --- FIN DEL BLOQUE DE DIAGNÓSTICO ---
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

}

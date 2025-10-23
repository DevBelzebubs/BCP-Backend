package com.backend.bcp.app.Prestamo.Infraestructure.adapters.out;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.backend.bcp.app.Shared.Application.Security.dto.in.UsuarioDTO;
import com.backend.bcp.app.Shared.Application.Security.ports.out.UserRepository;
import com.backend.bcp.app.Usuario.Application.ports.out.Cliente.NotificacionService;


@Service
public class JpaNotificacionServiceAdapter implements NotificacionService {
    
    @Autowired
    private JavaMailSender emailSender;
    private final UserRepository userRepository;
    
    public JpaNotificacionServiceAdapter(JavaMailSender emailSender, UserRepository userRepository) {
        this.emailSender = emailSender;
        this.userRepository = userRepository;
    }

    @Override
    public void notificarCliente(Long clienteId, String mensaje) {
        try{
            String emailCliente = userRepository.findById(clienteId).map(UsuarioDTO::correo).orElse("correo.por.defecto@ejemplo.com");
            SimpleMailMessage message = new SimpleMailMessage(); 
            message.setFrom("developerwebmythos@gmail.com");
            message.setTo(emailCliente); 
            message.setSubject("Actualización sobre su Solicitud de Crédito BCP"); 
            message.setText(mensaje);
            emailSender.send(message);
        }catch(Exception e){
            System.err.println("Error al enviar correo de notificación para Usuario ID " + clienteId + ": " + e.getMessage());
        }
    }
    
}

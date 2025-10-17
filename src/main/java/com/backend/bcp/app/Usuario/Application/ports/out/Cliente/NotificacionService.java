package com.backend.bcp.app.Usuario.Application.ports.out.Cliente;

public interface NotificacionService {
    void notificarCliente(Long clienteId, String mensaje);
}

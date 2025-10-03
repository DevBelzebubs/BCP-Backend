package com.backend.bcp.app.Usuario.Aplication.ports.out.Cliente;

public interface NotificacionService {
    void notificarCliente(Long clienteId, String mensaje);
}

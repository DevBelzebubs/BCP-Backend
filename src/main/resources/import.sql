INSERT INTO Servicio (ID_Servicio, Nombre, Descripcion, Recibo) VALUES (1, 'Servicio de Luz', 'Recibo de luz mensual', 120.50);
INSERT INTO Servicio (ID_Servicio, Nombre, Descripcion, Recibo) VALUES (2, 'Servicio de Agua', 'Recibo de agua potable', 55.00);
INSERT INTO Servicio (ID_Servicio, Nombre, Descripcion, Recibo) VALUES (3, 'Internet y Cable', 'Paquete de internet y cable', 150.00);
INSERT INTO Servicio (ID_Servicio, Nombre, Descripcion, Recibo) VALUES (4, 'Gas Natural', 'Recibo mensual de gas', 45.80);

-- USUARIO ADMIN (ID 1) (Contraseña '12345')
INSERT INTO usuario (ID_Usuario, Nombre, Contrasena, Correo, DNI, Direccion, Telefono) VALUES (1, 'admin', '$2a$10$beaU57lCk90J1NJI0Z4xd.W9jyZKkZRIOy2OrGAF5EvI1WlkDDb4K', 'admin@bcp.com', '12345678', 'Av. Admin 123', '999888777');
INSERT INTO empleado (ID_Empleado, Fecha_Contratacion, Salario, ID_Usuario) VALUES (1, CURDATE(), 9999.00, 1);
INSERT INTO Admin (ID_Admin, ID_Empleado) VALUES (1, 1);

-- USUARIO CLIENTE (ID 2) (Contraseña '12345')
INSERT INTO usuario (ID_Usuario, Nombre, Contrasena, Correo, DNI, Direccion, Telefono) VALUES (2, 'cliente_prueba', '$2a$10$8Lh3UZYaZtUQLjlN85XcjuMobOPwuahWVimzv43D0aFZ/tJPrGchq', 'cliente@ejemplo.com', '87654321', 'Calle Falsa 123', '987654321');
INSERT INTO cliente (ID_Cliente, Fecha_Registro, Usuario_ID_Usuario) VALUES (1, CURDATE(), 2);

-- CUENTA DEL CLIENTE 1
INSERT INTO Cuenta (ID_Cuenta, ID_Cliente, Tipo_Cuenta, Estado_Cuenta, Numero_Cuenta, Saldo) VALUES (1, 1, 'AHORROS', 'ACTIVO', '123-456789012-0-99', 1000.00);

-- PAGOS PENDIENTES DEL CLIENTE 1
INSERT INTO Pago (ID_Pago, TIPO_PAGO, Monto, Fecha, Estado, ID_Cliente, ID_Servicio, ID_Prestamo) VALUES (101, 'SERVICIO', 120.50, CURDATE(), 'PENDIENTE', 1, 1, NULL);
INSERT INTO Pago (ID_Pago, TIPO_PAGO, Monto, Fecha, Estado, ID_Cliente, ID_Servicio, ID_Prestamo) VALUES (102, 'SERVICIO', 55.00, CURDATE(), 'PENDIENTE', 1, 2, NULL);
INSERT INTO Pago (ID_Pago, TIPO_PAGO, Monto, Fecha, Estado, ID_Cliente, ID_Servicio, ID_Prestamo) VALUES (103, 'SERVICIO', 150.00, CURDATE(), 'PENDIENTE', 1, 3, NULL); 
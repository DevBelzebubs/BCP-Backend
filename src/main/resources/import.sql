INSERT INTO Servicio (ID_Servicio, Nombre, Descripcion, Recibo) VALUES (1, 'Servicio de Luz', 'Recibo de luz mensual', 120.50);
INSERT INTO Servicio (ID_Servicio, Nombre, Descripcion, Recibo) VALUES (2, 'Servicio de Agua', 'Recibo de agua potable', 55.00);
INSERT INTO Servicio (ID_Servicio, Nombre, Descripcion, Recibo) VALUES (3, 'Internet y Cable', 'Paquete de internet y cable', 150.00);
INSERT INTO Servicio (ID_Servicio, Nombre, Descripcion, Recibo) VALUES (4, 'Gas Natural', 'Recibo mensual de gas', 45.80);

-- La contraseña es '12345', pero está hasheada con BCrypt. 

INSERT INTO usuario (ID_Usuario, Nombre, Contrasena, Correo, DNI, Direccion, Telefono) VALUES (1, 'admin', '$2a$10$beaU57lCk90J1NJI0Z4xd.W9jyZKkZRIOy2OrGAF5EvI1WlkDDb4K', 'admin@bcp.com', '12345678', 'Av. Admin 123', '999888777');

INSERT INTO empleado (ID_Empleado, Fecha_Contratacion, Salario, ID_Usuario) VALUES (1, '2025-01-01', 5000.00, 1);

INSERT INTO Admin (ID_Admin, ID_Empleado) VALUES (1, 1);
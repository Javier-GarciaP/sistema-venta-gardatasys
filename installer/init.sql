CREATE TABLE IF NOT EXISTS usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(100),
    password VARCHAR(100) NOT NULL,
    rol VARCHAR(50)
);

-- Insertar usuario por defecto si no existe
MERGE INTO usuarios (id, nombre, correo, password, rol) KEY(id) VALUES (1, 'admin', 'admin@admin.com', 'admin', 'Administrador');

CREATE TABLE IF NOT EXISTS configuracion (
    id INT PRIMARY KEY,
    rif VARCHAR(50),
    nombre_negocio VARCHAR(200),
    nombre_propietario VARCHAR(200),
    telefono VARCHAR(50),
    municipio VARCHAR(100),
    estado VARCHAR(100),
    direccion VARCHAR(255),
    mensaje VARCHAR(255)
);

-- Migración: asegurar que telefono es VARCHAR (en caso de BD antigua con BIGINT)
ALTER TABLE configuracion ALTER COLUMN telefono VARCHAR(50);

MERGE INTO configuracion (id, rif, nombre_negocio, nombre_propietario, telefono, municipio, estado, direccion, mensaje) KEY(id) VALUES (1, 'J-123456789', 'Mi Negocio', 'Propietario', '0000-0000000', 'Municipio', 'Estado', 'Direccion', 'Gracias por su compra');

CREATE TABLE IF NOT EXISTS dinerocaja (
    id INT PRIMARY KEY,
    dinero DECIMAL(10,2)
);

MERGE INTO dinerocaja (id, dinero) KEY(id) VALUES (1, 0.00);

CREATE TABLE IF NOT EXISTS clientes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    dni VARCHAR(50),
    nombre VARCHAR(150),
    telefono VARCHAR(50),
    direccion VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS proveedor (
    id INT AUTO_INCREMENT PRIMARY KEY,
    rif VARCHAR(50),
    nombre VARCHAR(150),
    telefono VARCHAR(50),
    direccion VARCHAR(255)
);

-- Insertar proveedor por defecto si no existe
MERGE INTO proveedor (id, rif, nombre, telefono, direccion) KEY(id) VALUES (1, 'J-000000000', 'Proveedor General', '0000-0000000', 'Sin dirección');

CREATE TABLE IF NOT EXISTS productos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(50),
    nombre VARCHAR(150),
    medida VARCHAR(50),
    cantidad INT,
    precio DECIMAL(10,2),
    proveedor VARCHAR(150)
);

CREATE TABLE IF NOT EXISTS ventas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cliente VARCHAR(150),
    vendedor VARCHAR(150),
    total DECIMAL(10,2),
    metodo_pago VARCHAR(20) DEFAULT 'Debito',
    monto_pagado DECIMAL(10,2) DEFAULT 0,
    fecha VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS detalleventa (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cod_producto VARCHAR(50),
    cantidad INT,
    precio DECIMAL(10,2),
    ventaID INT
);

CREATE TABLE IF NOT EXISTS ventasdiarias (
    id INT AUTO_INCREMENT PRIMARY KEY,
    venta_id INT,
    total DECIMAL(10,2),
    tipo VARCHAR(50),
    descripcion VARCHAR(200),
    fecha VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS credito (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cliente VARCHAR(150),
    vendedor VARCHAR(150),
    total DECIMAL(10,2),
    idVentas INT,
    fecha VARCHAR(50)
);

-- Cargar productos iniciales
RUNSCRIPT FROM './productos-init.sql';

-- scriptzapatos.sql
DROP DATABASE IF EXISTS BDZapaton;
CREATE DATABASE BDZapaton;
USE BDZapaton;

CREATE TABLE zapato (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    marca VARCHAR(50),
    modelo VARCHAR(50),
    tamano VARCHAR(10),
    color VARCHAR(30),
    stock INT,
    precio DECIMAL(10,2)
);
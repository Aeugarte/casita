-- scriptzapatos.sql
DROP DATABASE IF EXISTS BDLibros;
CREATE DATABASE BDLibros;
USE BDLibros;

CREATE TABLE Libro (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(50),
    autor VARCHAR(50),
    stock INT,
    precio DECIMAL(10,2)
);
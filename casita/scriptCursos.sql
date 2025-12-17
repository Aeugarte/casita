-- scriptCursos.sql
DROP DATABASE IF EXISTS BDCursos;
CREATE DATABASE BDCursos;
USE BDCursos;
CREATE TABLE curso (
  ID INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(50),
  profesor VARCHAR(50), 
  horas INT,
  precio DECIMAL(10,2)
);

package Ej1;


import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Persona implements Serializable {
 private String dni;
 private String nombre;
 private int edad;
 public Persona() {}
 public Persona(String dni, String nombre, int edad) {
 this.dni = dni;
 this.nombre = nombre;
 this.edad = edad; }
 // Getters y setters
 public String getDni() { return dni; }
 public void setDni(String dni) { this.dni = dni; }
 public String getNombre() { return nombre; }
 public void setNombre(String nombre) { this.nombre = nombre; }
 public int getEdad() { return edad; }
 public void setEdad(int edad) { this.edad = edad; }
 @Override
 public String toString() {
 return nombre + " (" + dni + "), " + edad + " años"; }
 }

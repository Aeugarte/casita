package PruebaExamen;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.util.List;

public class Ej4 {
    public static void main(String[] args) {
        
        try {
            // Abrir archivos
            FileInputStream archivo1 = new FileInputStream("personasl.obj");
            FileOutputStream archivoCSV = new FileOutputStream("contactos.csv");
            
            // Crear los lectores/escritores
            ObjectInputStream lector1 = new ObjectInputStream(archivo1);
            PrintWriter escritor = new PrintWriter(archivoCSV);
            
            System.out.println("Archivos abiertos correctamente");
            
            // Leemos el objeto Personas (que contiene la lista)
            Personas personasObj = (Personas) lector1.readObject();
            
            // Obtenemos la lista de personas
            List<Persona> listaPersonas = personasObj.getPersonas();
            
            System.out.println("Leídas " + listaPersonas.size() + " personas del archivo");
            
            // Escribir el encabezado del CSV
            escritor.println("nombre,email");
            System.out.println("Encabezado CSV escrito");
            
            // Escribir cada persona en formato CSV
            for (Persona persona : listaPersonas) {
                try {
                    String nombre = persona.getNombre();
                    String email = persona.getEmail();
                    
                    if (email == null) email = "sin-email";
                    
                    escritor.println(nombre + "," + email);
                    System.out.println("Escrito: " + nombre + " - " + email);
                    
                } catch (Exception e) {
                    System.out.println("Error al procesar persona: " + e.getMessage());
                }
            }
            
            System.out.println("CSV creado correctamente. Total: " + listaPersonas.size() + " personas");
            
            // Cerrar recursos
            escritor.close();
            lector1.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
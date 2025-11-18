package PruebaExamen;


import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.RandomAccessFile;
import java.util.List;

public class Ej5 {
    public static void main(String[] args) {
        
        try {
            // 1. Abrir el archivo .obj de entrada
            FileInputStream archivoObj = new FileInputStream("personasl.obj");
            ObjectInputStream lector = new ObjectInputStream(archivoObj);
            
            // 2. Crear el archivo de acceso aleatorio
            RandomAccessFile archivoBinario = new RandomAccessFile("telefonos.bin", "rw");
            
            System.out.println("Archivos abiertos correctamente");
            
            // 3. Leer el objeto Personas
            Personas personasObj = (Personas) lector.readObject();
            List<Persona> listaPersonas = personasObj.getPersonas();
            
            System.out.println("Procesando " + listaPersonas.size() + " personas");
            
            // 4. Escribir cada persona en el archivo binario
            for (Persona persona : listaPersonas) {
                try {
                    String dni = persona.getDni();
                    int telefono = persona.getTelefono();
                    
                    // Asegurarnos de que el DNI tenga longitud fija
                    if (dni == null) {
                        dni = "SIN-DNI";
                    }
                    // Rellenar o recortar el DNI a 9 caracteres
                    dni = String.format("%-9s", dni).substring(0, 9);
                    
                    // Escribir en el archivo binario
                    archivoBinario.writeChars(dni);  // 9 chars = 18 bytes
                    archivoBinario.writeInt(telefono); // 4 bytes
                    
                    System.out.println("Escrito: " + dni.trim() + " - " + telefono);
                    
                } catch (Exception e) {
                    System.out.println("Error al procesar persona: " + e.getMessage());
                }
            }
            
            System.out.println("Archivo binario creado correctamente. Total: " + listaPersonas.size() + " registros");
            
            // 5. Cerrar recursos
            archivoBinario.close();
            lector.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
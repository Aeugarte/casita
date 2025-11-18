package PruebaExamen;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;




public class Ej1 {
 public static void main(String[] args) {
 // Crear un objeto ObjectMapper
 ObjectMapper mapper = new ObjectMapper();
mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
 try {
 // Leer el fichero JSON y convertirlo a un objeto de tipo Personas (ArrasList)
 Personas personas = mapper.readValue(new File("personas.json"), Personas.class);
 // Imprimir el objeto Persona
 

personas.mostrarPersonas();


//Guardar en archivo .ob

personas.guardarPersonas(personas, "personas1.obj");

System.out.println("Datos guardados correctamente");
 

 } catch (IOException e) {
 e.printStackTrace();
 }
 
 
 }
}
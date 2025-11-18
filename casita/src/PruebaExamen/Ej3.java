package PruebaExamen;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Ej3 {

	public static void main(String[] args) {
		
		
	
		
		try {
			//Abir los archivos
			FileInputStream archivo1 = new FileInputStream("personas1.obj");
			FileInputStream archivo2 = new FileInputStream("personas2.obj");
			FileOutputStream archivoFusionado = new FileOutputStream("personasl.obj");
			
			
			//Parte cre los lectores
			
			ObjectInputStream lector1 = new ObjectInputStream(archivo1);
			ObjectInputStream lector2 = new ObjectInputStream(archivo2);
			ObjectOutputStream escritor = new ObjectOutputStream(archivoFusionado);
			
			System.out.println("Archivos abiertos correctamente");
			
			
			// Intentamos leer un objeto de cada archivo
			Object objeto1 = lector1.readObject();
			Object objeto2 = lector2.readObject();
			
			//Si llega aqui ambos tienen datos
			
			System.out.println("Ambos tienen datos, podemos fusionar");
			
			try {
				
				
				//Creamos lista para guarda todas las personas
				List<Object> personas1 = new ArrayList<>();
				List<Object> personas2 = new ArrayList<>();
				
				// ¡No olvidemos agregar las primeras personas que ya leímos!
				personas1.add(objeto1);
				personas2.add(objeto2);
				
				//Leemos Todas la persona del primer archivo
				try {
					while (true) {
						Object persona = lector1.readObject();
						personas1.add(persona);
					}
				} catch (EOFException e) {
					// TODO: handle exception
					// Esto es normal - significa que ya leímos todas las personas
			        System.out.println("Leídas " + personas1.size() + " personas del primer archivo");
				}
				
				//Leemos Todas las personas del seugndo archivo
				
				try {
					while (true) {
						Object persona = lector2.readObject();
						personas2.add(persona);}
					
				} catch (EOFException e) {
					// TODO: handle exception
					  System.out.println("Leídas " + personas2.size() + " personas del segundo archivo");
				}
				
				
				//Creamos una lista para todas las personas fusionadas
				
				List<Object> todasLasPersonas = new ArrayList<>();
				
				//Agregamos todas las persona del primer archivo
				todasLasPersonas.addAll(personas1);
				
				//Agregamos todas las personas del segundo archivo
				todasLasPersonas.addAll(personas2);
				
				System.out.println("Tootal de personas fusionadas "+ todasLasPersonas.size());
				
				for (Object per : todasLasPersonas) {
					escritor.writeObject(per);
				}
				
				System.out.println("Fusionado pibe");
				
			} catch (ClassNotFoundException e) {
				
				System.out.println("Error: No se pudo encontrar la clase Persona");
			    return;
			}
			
			
			
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();	}
		
		
	
		
	}
}

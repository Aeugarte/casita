package Ej1;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//Serializable es como ponerle una etiqueta que dice: "Esta clase se puede guardar en archivos"
public class Personas implements Serializable {
	private List<Persona> personas;

	public Personas() {
		personas = new ArrayList<>();
	}

	public List<Persona> getPersonas() {
		return personas;
	}

	public void setPersonas(List<Persona> personas) {
		this.personas = personas;
	}

	public void agregarPersona(Persona p) {
		personas.add(p);
	}

	public void mostrarPersonas() {
		for (Persona p : personas) {
			System.out.println(p);

		}
	}

	public static void guardarPersonas(Personas personas, String nombreArchivo) {
	    // try-with-resources: Cierra automáticamente el stream al terminar
	    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nombreArchivo))) {
	        
	        // Esta línea es la que realmente guarda el objeto en el archivo
	        oos.writeObject(personas);
	        
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
}

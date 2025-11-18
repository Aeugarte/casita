package PruebaExamen;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Personas implements Serializable {

	
	private List<Persona> personas;

	public Personas() {
		personas = new ArrayList<>();
	}


	@XmlElement(name="persona")
	public List<Persona> getPersonas() {
		return personas;
	}

	public void setPersonas(List<Persona> personas) {
		this.personas = personas;
	}

	public void agregarPersona(Persona p) {
		personas.add(p);
	}

	public  void mostrarPersonas() {
		for (Persona p : personas) {
			System.out.println(p);
		}
	}
	
	public static void guardarPersonas(Personas personas, String nombreArchivo) {
		
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nombreArchivo))) {
			oos.writeObject(personas);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}

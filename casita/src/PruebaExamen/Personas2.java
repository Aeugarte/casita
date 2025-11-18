package PruebaExamen;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement 
public class Personas2 {

	private ArrayList<Persona> persona2 = new ArrayList<Persona>();

	public Personas2() {
		super();
	}

	public Personas2(ArrayList<Persona> persona2) {
		super();
		this.persona2 = persona2;
	}

	
	@XmlElementWrapper(name="persona2")
	@XmlElement(name="Persona")
	public ArrayList<Persona> getPersona2() {
		return persona2;
	}

	public void setPersona2(ArrayList<Persona> persona2) {
		this.persona2 = persona2;
	}
	
	public  void mostrarPersonas() {
		for (Persona p : persona2) {
			System.out.println(p.toString());
		}
	}
	
	
public static void guardarPersonas(Personas2 personas, String nombreArchivo) {
		
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nombreArchivo))) {
			oos.writeObject(personas);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	
}

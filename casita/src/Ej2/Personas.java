package Ej2;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Personas {

	ArrayList<Persona> listaPersonas = new ArrayList<Persona>();

	public Personas() {
		super();
	}

	public Personas(ArrayList<Persona> listaPersonas) {
		super();
		this.listaPersonas = listaPersonas;
	}

	@XmlElementWrapper(name = "listaPersonas")
	@XmlElement(name = "Persona")
	public ArrayList<Persona> getListaPersonas() {
		return listaPersonas;
	}

	public void setListaPersonas(ArrayList<Persona> listaPersonas) {
		this.listaPersonas = listaPersonas;
	}

	public void mostrarPersonas() {
		for (Persona p : listaPersonas)
			System.out.println(p.toString());
	}

	public void aniadirPersona(Persona p) {
		this.listaPersonas.add(p);
	}

}

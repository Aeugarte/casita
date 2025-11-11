package casita;



import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import javax.xml.bind.annotation.XmlElement;

@XmlRootElement(name = "concesionario")
public class Concesionario {
	
	List<Coche> coches;

	public Concesionario() {
		super();
	}

	@XmlElement(name = "coche")
	public List<Coche> getCoches() {
		return coches;
	}

	public void setCoches(List<Coche> coches) {
		this.coches = coches;
	}
	
	
	

}
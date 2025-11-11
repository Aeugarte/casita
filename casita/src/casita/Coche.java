package casita;



import javax.xml.bind.annotation.XmlAttribute;


import javax.xml.bind.annotation.XmlElement;

public class Coche {
	
	int id;
	
	String marca;
	String modelo;
	int precio;
	
	
	public Coche() {
		super();
	}


	public Coche(int id, String marca, String modelo, int precio) {
		super();
		this.id = id;
		this.marca = marca;
		this.modelo = modelo;
		this.precio = precio;
	}

	
	@XmlAttribute(name="id")
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}

	@XmlElement(name="marca")
	public String getMarca() {
		return marca;
	}


	public void setMarca(String marca) {
		this.marca = marca;
	}

	@XmlElement(name="modelo")
	public String getModelo() {
		return modelo;
	}


	public void setModelo(String modelo) {
		this.modelo = modelo;
	}


	public int getPrecio() {
		return precio;
	}


	public void setPrecio(int precio) {
		this.precio = precio;
	}
	
	

	
}
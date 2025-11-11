package casita;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Empresa {
	@XmlElement
 private String nombre;
	@XmlElement
 private String valor;
 
 
public Empresa() {
	super();
}
public Empresa(String nombre, String valor) {
	super();
	this.nombre = nombre;
	this.valor = valor;
}


public String getNombre() {
	return nombre;
}
public void setNombre(String nombre) {
	this.nombre = nombre;
}

public String getValor() {
	return valor;
}
public void setValor(String valor) {
	this.valor = valor;
}
 
 
}

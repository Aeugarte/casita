package vinted;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;

public class ProductoXml implements Serializable{
	private String nombre ,talla ,color, estado;
	private int precio;
	public ProductoXml() {
		super();
	}
	public ProductoXml(String nombre, String talla, String color, String estado, int precio) {
		super();
		this.nombre = nombre;
		this.talla = talla;
		this.color = color;
		this.estado = estado;
		this.precio = precio;
	}
	
	
	@XmlElement(name= "nombre")
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	@XmlElement(name= "talla")
	public String getTalla() {
		return talla;
	}
	public void setTalla(String talla) {
		this.talla = talla;
	}
	
	@XmlElement(name= "color")
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	
	@XmlElement(name= "estado")
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	
	@XmlElement(name= "precio")
	public int getPrecio() {
		return precio;
	}
	public void setPrecio(int precio) {
		this.precio = precio;
	}
	@Override
	public String toString() {
		return "ProductoXml [nombre=" + nombre + ", talla=" + talla + ", color=" + color + ", estado=" + estado
				+ ", precio=" + precio + "]";
	}

	
	
}

package vinted;

import java.io.Serializable;

public class Precio implements Serializable{

	private int idProducto, precio, coste, descuento;

	public Precio() {
		super();
	}

	public Precio(int idProducto, int precio, int coste, int descuento) {
		super();
		this.idProducto = idProducto;
		this.precio = precio;
		this.coste = coste;
		this.descuento = descuento;
	}

	public int getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
	}

	public int getPrecio() {
		return precio;
	}

	public void setPrecio(int precio) {
		this.precio = precio;
	}

	public int getCoste() {
		return coste;
	}

	public void setCoste(int coste) {
		this.coste = coste;
	}

	public int getDescuento() {
		return descuento;
	}

	public void setDescuento(int descuento) {
		this.descuento = descuento;
	}

	@Override
	public String toString() {
		return "Precio [idProducto=" + idProducto + ", precio=" + precio + ", coste=" + coste + ", descuento="
				+ descuento + "]";
	}
	
	
	
}

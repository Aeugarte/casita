package vinted;

import java.io.Serializable;

public class Ropa implements Serializable{
	private int idProducto, stock, precio, coste, descuento;
	private String nombre, categoria, talla, color, material, estado;

	public Ropa() {
		super();
	}

	public Ropa(int idProducto, int stock, int precio, int coste, int descuento, String nombre, String categoria,
			String talla, String color, String material, String estado) {
		super();
		this.idProducto = idProducto;
		this.stock = stock;
		this.precio = precio;
		this.coste = coste;
		this.descuento = descuento;
		this.nombre = nombre;
		this.categoria = categoria;
		this.talla = talla;
		this.color = color;
		this.material = material;
		this.estado = estado;
	}

	public int getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
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

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getTalla() {
		return talla;
	}

	public void setTalla(String talla) {
		this.talla = talla;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "Ropa [idProducto=" + idProducto + ", stock=" + stock + ", precio=" + precio + ", coste=" + coste
				+ ", descuento=" + descuento + ", nombre=" + nombre + ", categoria=" + categoria + ", talla=" + talla
				+ ", color=" + color + ", material=" + material + ", estado=" + estado + "]";
	}
}

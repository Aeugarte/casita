package vinted;

public class ProductoJSON {

	private int idProducto, precio;

	public ProductoJSON() {
		super();
		
	}

	public ProductoJSON(int idProducto, int precio) {
		super();
		this.idProducto = idProducto;
		this.precio = precio;
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
	
	
	
}

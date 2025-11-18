package vinted;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "productos")
public class ListaProductos {

	private List<ProductoXml> productos;

	public ListaProductos() {
		super();
	}

	@XmlElement(name= "producto")
	public List<ProductoXml> getProductos() {
		return productos;
	}

	public void setProductos(List<ProductoXml> productos) {
		this.productos = productos;
	}
	
	
}

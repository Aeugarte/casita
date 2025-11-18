package vinted;

import java.util.List;

import javax.xml.bind.JAXBException;

public class Ej4Main {

	
	public static void main(String[] args) throws ClassNotFoundException, JAXBException {
		
		// Creamos el gesto
		GeneradorXML gestor = new GeneradorXML();
		
		//Leer ropa.dat y convertir a ProductoXML
		List<ProductoXml> productos = gestor.leerRopaCompleta("ropa.dat");
		
		//Mostrar los productos
		
		System.out.println("Productos leidos: "+productos.size());
		
		
		//Generar el xml
		
		gestor.generarXML(productos, "productos.xml");
		
	}
	
	
}

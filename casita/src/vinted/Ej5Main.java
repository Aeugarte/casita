package vinted;

import java.util.List;

public class Ej5Main {

	public static void main(String[] args) throws ClassNotFoundException {
GeneradorJSON gestor = new GeneradorJSON();
		
		//Leer ropa.dat y convertir a ProductoXML
		List<ProductoJSON> productos = gestor.leerRoparJSON("ropa.dat");
		
		//Mostrar los productos
		
		System.out.println("Productos leidos: "+productos.size());
		
		
		//Generar el xml
		
		gestor.generarJSON(productos, "productos.json");
	}
	
}

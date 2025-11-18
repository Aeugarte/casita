package vinted;

import java.util.List;

public class Ej2Main {

	public static void main(String[] args) throws ClassNotFoundException {
Ej2 gestor = new Ej2();
		
		//1. Lleo el archivo ropa.dat
		List<Precio> miRopa = gestor.leerRopa("ropa.dat");
		
		//Mostrar lo que leimo para  comporbar que funka
		for (Precio prenda : miRopa) {
			System.out.println(prenda.getIdProducto()+"-"+prenda.getPrecio()+"€");
		}

	//Guardar en archivo binario
		
		gestor.guardarBinario(miRopa, "precio.dat");
		System.out.println("Proceso completado");
	}
}

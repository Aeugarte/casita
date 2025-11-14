package vinted;

import java.util.Iterator;
import java.util.List;

public class Main1 {
	 public static void main(String[] args) {
		Ej1 gestor = new Ej1();
		
		//1. Lleo el archivo csv
		List<Ropa> miRopa = gestor.leerCSV("ropa.csv");
		
		//Mostrar lo que leimo para  comporbar que funka
		for (Ropa prenda : miRopa) {
			System.out.println(prenda.getNombre()+"-"+prenda.getPrecio()+"€");
		}

	//Guardar en archivo binario
		
		gestor.guardarBinario(miRopa, "ropa.dat");
		System.out.println("Proceso completado");
		
		
	}
}

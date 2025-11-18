package vinted;

import java.util.List;
import java.util.Scanner;

public class Ej3Main {

	public static void main(String[] args) throws ClassNotFoundException {
		Ej3 gestor = new Ej3();
		Scanner leer = new Scanner(System.in);
		
		//Leer los precios
		
		List<Precio> miRopa = gestor.leerRopita("precio.dat");
		
		System.out.println("Todos los Productos");
		//Mostrar lo que leimo para  comporbar que funka
				for (Precio prenda : miRopa) {
					System.out.println(prenda.getIdProducto()+"-"+prenda.getPrecio()+"€");
				}
	
				
		//Pedir id por consola
		System.out.println("Buscar Poducto"
				+ "\n Introduce el Id del producto:");
		int idproducto = leer.nextInt();
		
		boolean encotrado = false;
		for (Precio precio : miRopa) {
			if (precio.getIdProducto() == idproducto) {
				System.out.println("Producto econtrado:"
						+"\n Benficio" + beneficio(precio.getIdProducto(),precio.getDescuento() , precio.getCoste()));
				encotrado = true;
				break;
			}
		}
		
		if(!encotrado) {
			System.out.println("Producto con id "+ idproducto+" no encontrado");
		}
		
		leer.close();
		
	}

	private static double beneficio(int Precio,int descuento,int coste) {
		
		double formula;
		
		formula = Precio-(Precio*descuento/100)-coste;
		
		return formula;
	}
}

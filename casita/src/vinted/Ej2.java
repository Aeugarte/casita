package vinted;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Ej2 {

	
public List<Precio> leerRopa(String rutaArchivo) throws ClassNotFoundException{
    List<Precio> listaPrecio = new ArrayList<>() ;
    
    try {
    	//leemos el archivo binario para lectura
    	FileInputStream archivo = new FileInputStream(rutaArchivo);
    	ObjectInputStream  reader = new ObjectInputStream(archivo);
    	
    	// paso 2: Leer la lista de objetos ropa
		List<Ropa> listaRopa = (List<Ropa>) reader.readObject();
    	
		
		//Paso 3: convertir Cada Ropa en un objeto Precio
		
		for (Ropa prenda : listaRopa) {
			Precio precio = new Precio(
					prenda.getIdProducto(),
					prenda.getPrecio(),
					prenda.getCoste(),
					prenda.getDescuento()
					);
			listaPrecio.add(precio);
		}
		
		reader.close();
		
		System.out.println("Se leyeron "+ listaPrecio.size()+ "prenda del archivo CSV");
    	
    	
	} catch (IOException e) {
		// TODO: handle exception
		e.printStackTrace();
	}

    return listaPrecio;
}  


public void guardarBinario(List<Precio> listaPrecio, String nombreArchivo) {
  
  try {
	  FileOutputStream archivo = new FileOutputStream(nombreArchivo);
	  ObjectOutputStream escritor = new ObjectOutputStream(archivo);
	  
	  //guardamos toa la lista
	  
	  escritor.writeObject(listaPrecio);
	  
	  escritor.close();
	  
	  System.out.println("Datos guardado en "+ nombreArchivo);
} catch (Exception e) {
	// TODO: handle exception
}
  

}
}


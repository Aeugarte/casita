package vinted;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class Ej3 {

	public List<Precio> leerRopita(String rutaArchivo) throws ClassNotFoundException{
	    List<Precio> listaPrecio = new ArrayList<>() ;
	    
	    try {
	    	//leemos el archivo binario para lectura
	    	FileInputStream archivo = new FileInputStream(rutaArchivo);
	    	ObjectInputStream  reader = new ObjectInputStream(archivo);
	    	
	    	// paso 2: Leer la lista de objetos Precio directamen
		 listaPrecio = (List<Precio>) reader.readObject();
			
			reader.close();
			
			System.out.println("Se leyeron "+ listaPrecio.size()+ "precio del archivo binario");
	    	
	    	
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	    return listaPrecio;
	}
	
  

	
	


}

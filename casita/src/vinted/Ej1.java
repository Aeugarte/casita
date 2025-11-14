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

public class Ej1 {

	public List<Ropa> leerCSV(String rutaArchivo){
	       List<Ropa> listaRopa = new ArrayList<>() ;
	        
	        try {
	        	//leemos el archivo
				BufferedReader reader = new BufferedReader(new FileReader(rutaArchivo));
				String linea;
				
				//leemos cada liena del archivo
				while((linea = reader.readLine())!= null) {
					String[] datos = linea.split(";");
					
					//Creamo un objeto ropa
				
					Ropa prenda = new Ropa();
					prenda.setIdProducto(Integer.parseInt(datos[0]));
					prenda.setNombre(datos[1]);
					prenda.setCategoria(datos[2]);
					prenda.setTalla(datos[3]);
					prenda.setColor(datos[4]);
					prenda.setMaterial(datos[5]);
					prenda.setStock(Integer.parseInt(datos[6]));
					prenda.setPrecio(Integer.parseInt(datos[7]));
					prenda.setCoste(Integer.parseInt(datos[8]));
					prenda.setEstado(datos[9]);
					prenda.setDescuento(Integer.parseInt(datos[10]));
					
					// Añadimos la prenda a la lista
					listaRopa.add(prenda);
				}
				
				reader.close();
				
				System.out.println("Se leyeron "+ listaRopa.size()+ "prenda del archivo CSV");
	        	
	        	
			} catch (IOException e) {
				// TODO: handle exception
				e.printStackTrace();
			}

	        return listaRopa;
}  
	
	  
	  public void guardarBinario(List<Ropa> listaRopa, String nombreArchivo) {
		  
		  try {
			  FileOutputStream archivo = new FileOutputStream(nombreArchivo);
			  ObjectOutputStream escritor = new ObjectOutputStream(archivo);
			  
			  //guardamos toa la lista
			  
			  escritor.writeObject(listaRopa);
			  
			  escritor.close();
			  
			  System.out.println("Datos guardado en "+ nombreArchivo);
		} catch (Exception e) {
			// TODO: handle exception
		}
		  
	  }
	  
	  
}


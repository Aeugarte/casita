package vinted;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class GeneradorJSON {

	public List<ProductoJSON> leerRoparJSON(String rutaArchivo) throws ClassNotFoundException{
		
		List<ProductoJSON> listaProducto = new ArrayList<>();
		
		 try {
		    	//leemos el archivo binario para lectura
		    	FileInputStream archivo = new FileInputStream(rutaArchivo);
		    	ObjectInputStream  reader = new ObjectInputStream(archivo);
		    	
		    	// paso 2: Leer la lista de objetos ropa
				List<Ropa> listaRopa = (List<Ropa>) reader.readObject();
		    	
				
				//Paso 3: convertir Cada Ropa en un objeto Producto
				
				for (Ropa prenda : listaRopa) {
					
					ProductoJSON precio = new ProductoJSON(
							prenda.getIdProducto(),
							prenda.getPrecio()
							);
					listaProducto.add(precio);
				}
				
				reader.close();
				
				System.out.println("Se leyeron "+ listaProducto.size()+ "prenda del archivo CSV");
		    	
		    	
			} catch (IOException e) {
				// TODO: handle exception
				e.printStackTrace();
			}

		    return listaProducto;
		
	}
	
	
	public void generarJSON(List<ProductoJSON> productos, String nombreArchivo) {
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			
			 // CONFIGURACIÓN OPCIONAL (para que el JSON se vea bonito)
	        mapper.enable(SerializationFeature.INDENT_OUTPUT);
	        
	        // Guardar la lista de productos en el archivo JSON
			mapper.writeValue(new File(nombreArchivo), productos);
			
			 System.out.println("Archivo JSON generado: " + nombreArchivo);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
}

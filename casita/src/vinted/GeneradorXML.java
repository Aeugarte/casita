package vinted;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class GeneradorXML {
	public List<ProductoXml> leerRopaCompleta(String rutaArchivo) throws ClassNotFoundException{
		
		List<ProductoXml> listaProducto = new ArrayList<>();
		
		
		  try {
		    	//leemos el archivo binario para lectura
		    	FileInputStream archivo = new FileInputStream(rutaArchivo);
		    	ObjectInputStream  reader = new ObjectInputStream(archivo);
		    	
		    	// paso 2: Leer la lista de objetos ropa
				List<Ropa> listaRopa = (List<Ropa>) reader.readObject();
		    	
				
				//Paso 3: convertir Cada Ropa en un objeto Producto
				
				for (Ropa prenda : listaRopa) {
					
					ProductoXml precio = new ProductoXml(
							prenda.getNombre(),
							prenda.getTalla(),
							prenda.getEstado(),
							prenda.getColor(),
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
	
	public void generarXML(List<ProductoXml> productos, String nombreArchivo) throws JAXBException {
		
		
		try {
			//contenedor
			ListaProductos lista = new ListaProductos();
			lista.setProductos(productos);
			//creamos el jaxc
			JAXBContext jaxbContext = JAXBContext.newInstance(ListaProductos.class);
			Marshaller marshaller = jaxbContext.createMarshaller();
			
			
			//
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);
			// Convertir objeto a XML y mostrar en consola 
			 marshaller.marshal(lista, new File(nombreArchivo)); 
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	
}

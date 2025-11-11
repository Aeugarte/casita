package casita;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

public class CrearXMLJAXB {

	public static void main(String[] args) throws IOException {
		
		//Si
		try {
			List<String> lineas = Files.readAllLines(Paths.get("cotizacion2.txt"));
			
			
			/*for(String linea : lineas) {
				System.out.println("Linea leida"+ linea);
			}*/
			
			
			ListaEmpresas listaEmpresas = new ListaEmpresas();
			
			for (String linea: lineas) {
				String[] partes = linea.split(";");
				
				if(partes.length == 2) {
					// partes[0] es el nombre, partes[1]es el valor
					
					String nombre = partes[0].trim();
					String valor = partes[1].trim().replace("â‚¬", "").replace("€", "");
					
					//Crear obejto Empresa y agrarlo a la lista
					
					Empresa empresa = new Empresa(nombre, valor);
					
					listaEmpresas.agregarEmpresa(empresa);
				}
				
				
			}
			

			JAXBContext contexto = JAXBContext.newInstance(ListaEmpresas.class);
			
			Marshaller marshaller = contexto.createMarshaller();
			
			//Esto es para que se vea mejor
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			
			
			marshaller.marshal(listaEmpresas, new File("empresas.xml"));
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
}

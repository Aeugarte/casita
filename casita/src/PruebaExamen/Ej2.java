package PruebaExamen;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class Ej2 {

	
	public static void main(String[] args) {
		try {
			//Instanciar un contexto de la clase JAXBContext con la clase RootElement
			 JAXBContext contexto = JAXBContext.newInstance(Personas.class);
			 
			 
			 //Crear un unmarshaller que convierte el XML en JavaBeans.
			 Unmarshaller um = contexto.createUnmarshaller();
			 
			 
			 //Realizar la deserialización llamando al método unmarshal del marshaller.
			 Personas miColeccion = (Personas) um.unmarshal(new File("personas.xml"));
			 
			 miColeccion.mostrarPersonas();;
			 
			 miColeccion.guardarPersonas(miColeccion, "personas2.obj");
			 System.out.println("Guardado correctamente");
			 
			} catch (JAXBException e) {
			}
	}
	
}

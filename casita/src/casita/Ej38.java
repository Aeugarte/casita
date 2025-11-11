package casita;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class Ej38 {

	public static void main(String[] args) throws JAXBException {
		
		// Preparar JAXB para leer el XML
        JAXBContext context = JAXBContext.newInstance(Concesionario.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        // Leer el archivo XML y convertirlo en objetos Java
        Concesionario concesionario = (Concesionario) unmarshaller.unmarshal(new File("concesionario.xml"));
        
        
        //Aqui bus el coche mas barato
        
        Coche masBarato = null;
        for(Coche coche : concesionario.getCoches()) {
        	if (masBarato == null ||coche.getPrecio() < masBarato.getPrecio()) {
				masBarato = coche;
			}
        }
        
        
      //Muestra el mas barato
            System.out.println("ID: " + masBarato.getId());
            System.out.println("Marca: " + masBarato.getMarca());
            System.out.println("Modelo: " + masBarato.getModelo());
            System.out.println("Precio: " + masBarato.getPrecio());
            System.out.println("---");
        /*
Primero: Las clases Concesionario y Coche usan anotaciones para que JAXB pueda leer el XML.​
Segundo: El programa lee el archivo usando Unmarshaller.​
Tercero: Recorres todos los coches y guardas el que tiene el precio más bajo.
Por último: Lo imprimes en la consola. Así ves el modelo, la marca y el precio del coche más barato.
         * */
	}
}
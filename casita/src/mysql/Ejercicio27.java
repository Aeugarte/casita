package mysql;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Ejercicio27 {
    public static void main(String[] args) {
        String archivoXML = "ampliarAlumnos.xml";
        
        System.out.println("  EJERCICIO 27: IMPORTAR ALUMNOS DESDE XML");
        System.out.println("  Archivo: " + archivoXML);

        
        System.out.println("Analizando el archivo XML...");
        System.out.println("Estructura encontrada:");
        System.out.println("  - Elemento principal: <alumnos>");
        System.out.println("  - Cada alumno tiene: <nombre>, <curso>, <telefono>");
        System.out.println("  - Total de alumnos en el XML: " + contarAlumnosXML(archivoXML) + "\n");
        
        int alumnosImportados = importarAlumnosDesdeXML(archivoXML);
        
        System.out.println("\n" + "=".repeat(50));
        if (alumnosImportados > 0) {
            System.out.println("IMPORTACION COMPLETADA CON EXITO");
            System.out.println("RESUMEN:");
            System.out.println("  - Alumnos procesados: " + alumnosImportados);
            System.out.println("  - Fecha: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
        } else {
            System.out.println("No se importaron nuevos alumnos");
            System.out.println("(Posiblemente ya existian en la base de datos)");
        }
        System.out.println("=".repeat(50));
    }
    
    /*
     * Este metodo lee un archivo XML con datos de alumnos y los importa
     * a la base de datos MySQL. Usamos DOM para parsear el XML.
     */
    public static int importarAlumnosDesdeXML(String nombreArchivo) {
        int contador = 0;
        
        // SQL para insertar alumnos en la base de datos
        // ON DUPLICATE KEY UPDATE sirve para actualizar si ya existe
        String sql = "INSERT INTO alumno (nombre, telefono) VALUES (?, ?) " +
                     "ON DUPLICATE KEY UPDATE telefono = VALUES(telefono)";
        
        try {
            // Preparar el parser de XML (DOM)
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            
            System.out.println("Leyendo y parseando el archivo XML...");
            Document document = builder.parse(nombreArchivo);
            document.getDocumentElement().normalize();
            
            // Obtener todos los elementos <alumno> del XML
            NodeList listaAlumnos = document.getElementsByTagName("alumno");
            System.out.println("Encontrados " + listaAlumnos.getLength() + " alumnos en el archivo XML\n");
            
            // Conectar a la base de datos MySQL
            try (Connection conn = ConexionAlumnos.conectar();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                
                if (conn == null) {
                    System.out.println("ERROR: No se pudo conectar a la base de datos");
                    return 0;
                }
                
                // Desactivamos el autocommit para hacer una transaccion
                // Asi si falla algo, no se guarda nada
                conn.setAutoCommit(false);
                
                System.out.println("Procesando alumnos del XML...");
                System.out.println("+---------------------------------------------------+");
                
                // Recorrer cada alumno del XML
                for (int i = 0; i < listaAlumnos.getLength(); i++) {
                    Element alumnoElem = (Element) listaAlumnos.item(i);
                    
                    try {
                        // Sacar los datos de cada etiqueta XML
                        String nombreCompleto = getElementText(alumnoElem, "nombre");
                        String curso = getElementText(alumnoElem, "curso");
                        String telefono = getElementText(alumnoElem, "telefono");
                        
                        // Mostrar en pantalla lo que estamos procesando
                        System.out.printf("| %2d. %-25s | Curso: %-5s | Tel: %-12s |%n",
                            i + 1, 
                            nombreCompleto.length() > 25 ? nombreCompleto.substring(0, 22) + "..." : nombreCompleto,
                            curso, 
                            telefono);
                        
                        // Separar nombre y apellidos (el XML viene con ambos juntos)
                        String nombre = nombreCompleto;
                        String apellidos = "";
                        
                        int espacio = nombreCompleto.indexOf(' ');
                        if (espacio > 0) {
                            nombre = nombreCompleto.substring(0, espacio);
                            apellidos = nombreCompleto.substring(espacio + 1);
                        }
                        
                        // Usamos una sentencia SQL mas completa para insertar todos los campos
                        String sqlCompleto = "INSERT INTO alumno (nombre, apellidos, edad, email, telefono) " +
                                           "VALUES (?, ?, ?, ?, ?) " +
                                           "ON DUPLICATE KEY UPDATE telefono = VALUES(telefono)";
                        
                        try (PreparedStatement pstmtCompleto = conn.prepareStatement(sqlCompleto)) {
                            pstmtCompleto.setString(1, nombre);
                            pstmtCompleto.setString(2, apellidos);
                            pstmtCompleto.setInt(3, 20); // Edad por defecto (20 años)
                            pstmtCompleto.setString(4, generarEmail(nombre, apellidos)); // Generamos email
                            pstmtCompleto.setString(5, telefono);
                            
                            // Ejecutar la insercion
                            int filasAfectadas = pstmtCompleto.executeUpdate();
                            
                            if (filasAfectadas > 0) {
                                contador++;
                                if (filasAfectadas == 1) {
                                    System.out.print("     (NUEVO)");
                                } else {
                                    System.out.print("     (ACTUALIZADO)");
                                }
                            } else {
                                System.out.print("     (SIN CAMBIOS)");
                            }
                        }
                        
                    } catch (Exception e) {
                        System.out.println("     ERROR: " + e.getMessage());
                    }
                    System.out.println();
                }
                
                System.out.println("+---------------------------------------------------+");
                
                // Si todo fue bien, hacemos commit para guardar los cambios
                conn.commit();
                System.out.println("Cambios guardados en la base de datos");
                
            } catch (Exception e) {
                System.out.println("ERROR en la base de datos: " + e.getMessage());
                if (e.getMessage() != null && e.getMessage().contains("Duplicate entry")) {
                    System.out.println("  (Algunos alumnos ya existen en la base de datos)");
                }
            }
            
        } catch (Exception e) {
            System.out.println("ERROR al procesar el XML: " + e.getMessage());
            e.printStackTrace();
        }
        
        return contador;
    }
    
    /*
     * metodo auxiliar para sacar el texto de una etiqueta XML
     * ejemplo: <nombre>Juan</nombre> -> devuelve "Juan"
     */
    private static String getElementText(Element parent, String tagName) {
        NodeList nodes = parent.getElementsByTagName(tagName);
        if (nodes.getLength() > 0) {
            return nodes.item(0).getTextContent().trim();
        }
        return "";
    }
    
    /*
     * Cuenta cuantos alumnos hay en el archivo XML
     * sin necesidad de procesarlos todos
     */
    private static int contarAlumnosXML(String nombreArchivo) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(nombreArchivo);
            NodeList alumnos = doc.getElementsByTagName("alumno");
            return alumnos.getLength();
        } catch (Exception e) {
            return 0;
        }
    }
    
    /*
     * Genera un email automatico a partir del nombre y apellidos
     * Ejemplo: Juan Perez -> juan.perez@instituto.edu
     */
    private static String generarEmail(String nombre, String apellidos) {
        if (nombre == null || nombre.isEmpty()) return null;
        
        String email = nombre.toLowerCase();
        if (!apellidos.isEmpty()) {
            // Tomamos solo el primer apellido
            String primerApellido = apellidos.split(" ")[0].toLowerCase();
            email += "." + primerApellido;
        }
        email += "@instituto.edu";
        
        return email;
    }
    
    /*
     * Metodo extra para validar que el XML esta bien formado
     * y tiene la estructura correcta
     */
    public static void validarXML(String nombreArchivo) {
        System.out.println("\nVALIDANDO ESTRUCTURA DEL XML:");
        
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(nombreArchivo);
            
            System.out.println("OK - XML bien formado");
            System.out.println("  Elemento raiz: " + doc.getDocumentElement().getNodeName());
            
            NodeList alumnos = doc.getElementsByTagName("alumno");
            System.out.println("  Numero de elementos <alumno>: " + alumnos.getLength());
            
            if (alumnos.getLength() > 0) {
                Element primerAlumno = (Element) alumnos.item(0);
                NodeList hijos = primerAlumno.getChildNodes();
                
                System.out.println("  Elementos dentro de <alumno>:");
                for (int i = 0; i < hijos.getLength(); i++) {
                    if (hijos.item(i).getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                        Element hijo = (Element) hijos.item(i);
                        System.out.println("    - <" + hijo.getNodeName() + ">");
                    }
                }
            }
            
        } catch (Exception e) {
            System.out.println("ERROR - XML mal formado: " + e.getMessage());
        }
    }
}
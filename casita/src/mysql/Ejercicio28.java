package mysql;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Ejercicio28 {
    public static void main(String[] args) {
        String archivoSalida = "totalAlumno.xml";
        

        System.out.println("  EJERCICIO 28: EXPORTAR ALUMNOS A XML");
        System.out.println("  Archivo de salida: " + archivoSalida);

        
        System.out.println("Preparando exportacion...");
        
        boolean exportado = exportarAlumnosAXML(archivoSalida);
        
        if (exportado) {
            File archivo = new File(archivoSalida);
            System.out.println("\n" + "=".repeat(60));
            System.out.println("EXPORTACION COMPLETADA EXITOSAMENTE");
            System.out.println("=".repeat(60));
            System.out.println("INFORMACION DEL ARCHIVO GENERADO:");
            System.out.println("  - Ruta: " + archivo.getAbsolutePath());
            System.out.println("  - Tamano: " + archivo.length() + " bytes");
            System.out.println("  - Fecha de generacion: " + 
                              new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
            System.out.println("=".repeat(60));
            
            // Mostrar un poco del contenido generado
            mostrarVistaPrevia(archivoSalida);
        } else {
            System.out.println("\nERROR en la exportacion");
            System.out.println("  Revisa la conexion a la base de datos");
        }
    }
    
    /*
     * Este metodo exporta todos los alumnos de la base de datos MySQL
     * a un archivo XML. Es el ejercicio contrario al 27.
     * Generamos un XML bien estructurado con toda la informacion.
     */
    public static boolean exportarAlumnosAXML(String nombreArchivo) {
        try {
            // Creamos un documento XML vacio
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            
            // Elemento raiz del XML con algunos atributos
            Element raiz = doc.createElement("instituto");
            raiz.setAttribute("nombre", "IES Augustobriga");
            raiz.setAttribute("fecha_exportacion", 
                            new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            doc.appendChild(raiz);
            
            // Seccion de informacion sobre la exportacion
            Element info = doc.createElement("informacion_exportacion");
            raiz.appendChild(info);
            
            Element titulo = doc.createElement("titulo");
            titulo.appendChild(doc.createTextNode("Exportacion de Alumnos - Acceso a Datos"));
            info.appendChild(titulo);
            
            Element descripcion = doc.createElement("descripcion");
            descripcion.appendChild(doc.createTextNode(
                "Listado completo de alumnos exportado desde la base de datos MySQL"));
            info.appendChild(descripcion);
            
            Element autor = doc.createElement("autor");
            autor.appendChild(doc.createTextNode("Sistema de Gestion Academica"));
            info.appendChild(autor);
            
            // Conectar a la base de datos para sacar los datos
            try (Connection conn = ConexionAlumnos.conectar()) {
                if (conn == null) {
                    System.out.println("ERROR: No hay conexion a la base de datos");
                    return false;
                }
                
                System.out.println("OK - Conectado a la base de datos 'Alumnos'");
                
                // Primero contamos cuantos alumnos hay
                int totalAlumnos = 0;
                try (Statement stmtCount = conn.createStatement();
                     ResultSet rsCount = stmtCount.executeQuery("SELECT COUNT(*) as total FROM alumno")) {
                    if (rsCount.next()) {
                        totalAlumnos = rsCount.getInt("total");
                    }
                }
                
                System.out.println("Total de alumnos a exportar: " + totalAlumnos);
                
                if (totalAlumnos == 0) {
                    System.out.println("AVISO: No hay alumnos para exportar");
                    Element sinDatos = doc.createElement("mensaje");
                    sinDatos.appendChild(doc.createTextNode("No hay alumnos en la base de datos"));
                    raiz.appendChild(sinDatos);
                } else {
                    // Creamos el elemento que contendra todos los alumnos
                    Element alumnosElem = doc.createElement("alumnos");
                    raiz.appendChild(alumnosElem);
                    
                    // Consulta SQL para sacar todos los alumnos
                    String sql = "SELECT id, nombre, apellidos, edad, email, telefono " +
                                 "FROM alumno ORDER BY apellidos, nombre";
                    
                    try (Statement stmt = conn.createStatement();
                         ResultSet rs = stmt.executeQuery(sql)) {
                        
                        System.out.println("Exportando alumnos...");
                        System.out.print("Progreso: [");
                        
                        int contador = 0;
                        int progresoAnterior = -1;
                        
                        // Para cada alumno en la base de datos...
                        while (rs.next()) {
                            contador++;
                            
                            // Barra de progreso simple
                            int progreso = (contador * 100) / totalAlumnos;
                            if (progreso / 10 > progresoAnterior / 10) {
                                System.out.print("#");
                                progresoAnterior = progreso;
                            }
                            
                            // Creamos un elemento <alumno> por cada registro
                            Element alumno = doc.createElement("alumno");
                            alumno.setAttribute("id", String.valueOf(rs.getInt("id")));
                            
                            // Nombre
                            Element nombre = doc.createElement("nombre");
                            nombre.appendChild(doc.createTextNode(rs.getString("nombre")));
                            alumno.appendChild(nombre);
                            
                            // Apellidos
                            Element apellidos = doc.createElement("apellidos");
                            apellidos.appendChild(doc.createTextNode(rs.getString("apellidos")));
                            alumno.appendChild(apellidos);
                            
                            // Edad (solo si tiene)
                            int edad = rs.getInt("edad");
                            if (!rs.wasNull() && edad > 0) {
                                Element elemEdad = doc.createElement("edad");
                                elemEdad.appendChild(doc.createTextNode(String.valueOf(edad)));
                                alumno.appendChild(elemEdad);
                            }
                            
                            // Email (solo si tiene)
                            String email = rs.getString("email");
                            if (email != null && !email.trim().isEmpty()) {
                                Element elemEmail = doc.createElement("email");
                                elemEmail.appendChild(doc.createTextNode(email));
                                alumno.appendChild(elemEmail);
                            }
                            
                            // Telefono (solo si tiene)
                            String telefono = rs.getString("telefono");
                            if (telefono != null && !telefono.trim().isEmpty()) {
                                Element elemTelefono = doc.createElement("telefono");
                                elemTelefono.appendChild(doc.createTextNode(telefono));
                                alumno.appendChild(elemTelefono);
                            }
                            
                            // Fecha de registro (la ponemos como la de hoy)
                            Element fechaRegistro = doc.createElement("fecha_registro");
                            fechaRegistro.appendChild(doc.createTextNode(
                                new SimpleDateFormat("yyyy-MM-dd").format(new Date())));
                            alumno.appendChild(fechaRegistro);
                            
                            // Anadimos este alumno al elemento <alumnos>
                            alumnosElem.appendChild(alumno);
                        }
                        
                        System.out.println("] 100%");
                        System.out.println("OK - " + contador + " alumnos exportados");
                        
                        // Anadimos estadisticas al final del XML
                        Element estadisticas = doc.createElement("estadisticas");
                        raiz.appendChild(estadisticas);
                        
                        Element total = doc.createElement("total_alumnos");
                        total.appendChild(doc.createTextNode(String.valueOf(contador)));
                        estadisticas.appendChild(total);
                        
                        Element fechaExport = doc.createElement("fecha_hora_exportacion");
                        fechaExport.appendChild(doc.createTextNode(
                            new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date())));
                        estadisticas.appendChild(fechaExport);
                        
                    } catch (Exception e) {
                        System.out.println("ERROR al obtener datos: " + e.getMessage());
                        return false;
                    }
                }
                
            } catch (Exception e) {
                System.out.println("ERROR de conexion: " + e.getMessage());
                return false;
            }
            
            // Ahora guardamos el documento XML en un archivo
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            
            // Configuramos para que el XML tenga buena indentacion
            transformer.setOutputProperty(javax.xml.transform.OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            
            // Codificacion UTF-8 para que salgan bien las tildes
            transformer.setOutputProperty(javax.xml.transform.OutputKeys.ENCODING, "UTF-8");
            
            // Fuente (nuestro documento) y destino (el archivo)
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(nombreArchivo));
            
            // Transformar y guardar
            transformer.transform(source, result);
            
            return true;
            
        } catch (Exception e) {
            System.out.println("ERROR durante la exportacion XML: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /*
     * Metodo para mostrar las primeras lineas del XML generado
     * Asi podemos ver que se ha creado correctamente
     */
    private static void mostrarVistaPrevia(String nombreArchivo) {
        File archivo = new File(nombreArchivo);
        
        if (archivo.exists()) {
            System.out.println("\nVISTA PREVIA DEL ARCHIVO GENERADO:");
            System.out.println("(primeras 15 lineas)");
            System.out.println("-".repeat(60));
            
            try (java.io.BufferedReader br = new java.io.BufferedReader(
                 new java.io.FileReader(archivo))) {
                
                for (int i = 0; i < 15; i++) {
                    String linea = br.readLine();
                    if (linea != null) {
                        System.out.println(linea);
                    } else {
                        break;
                    }
                }
                
                System.out.println("-".repeat(60));
                System.out.println("... (archivo continua)");
                
            } catch (Exception e) {
                System.out.println("No se pudo leer el archivo para vista previa");
            }
        }
    }
    
    /*
     * Version alternativa: exporta alumnos con sus notas tambien
     * (Por si el profesor quiere ver algo mas avanzado)
     */
    public static boolean exportarAlumnosCompleto(String nombreArchivo) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            
            Element raiz = doc.createElement("exportacion_completa");
            raiz.setAttribute("fecha", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            doc.appendChild(raiz);
            
            try (Connection conn = ConexionAlumnos.conectar()) {
                if (conn == null) return false;
                
                // Consulta que junta alumnos con sus notas
                String sql = "SELECT a.*, n.codigo_modulo, n.nota, m.nombre as nombre_modulo " +
                           "FROM alumno a " +
                           "LEFT JOIN nota n ON a.id = n.id_alumno " +
                           "LEFT JOIN modulo m ON n.codigo_modulo = m.codigo " +
                           "ORDER BY a.apellidos, a.nombre";
                
                try (Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery(sql)) {
                    
                    int alumnoActual = -1;
                    Element alumnoElement = null;
                    
                    while (rs.next()) {
                        int idAlumno = rs.getInt("id");
                        
                        // Si es un alumno nuevo, creamos su elemento
                        if (idAlumno != alumnoActual) {
                            if (alumnoElement != null) {
                                raiz.appendChild(alumnoElement);
                            }
                            
                            alumnoActual = idAlumno;
                            alumnoElement = doc.createElement("alumno");
                            
                            // Datos basicos del alumno
                            Element id = doc.createElement("id");
                            id.appendChild(doc.createTextNode(String.valueOf(idAlumno)));
                            alumnoElement.appendChild(id);
                            
                            Element nombre = doc.createElement("nombre");
                            nombre.appendChild(doc.createTextNode(rs.getString("nombre")));
                            alumnoElement.appendChild(nombre);
                            
                            // ... resto de datos basicos
                            
                            // Elemento para las notas
                            Element notas = doc.createElement("notas");
                            alumnoElement.appendChild(notas);
                        }
                        
                        // Anadir nota si tiene
                        String codigoModulo = rs.getString("codigo_modulo");
                        if (codigoModulo != null) {
                            Element nota = doc.createElement("nota");
                            
                            Element modulo = doc.createElement("modulo");
                            modulo.appendChild(doc.createTextNode(rs.getString("nombre_modulo")));
                            nota.appendChild(modulo);
                            
                            Element calificacion = doc.createElement("calificacion");
                            calificacion.appendChild(doc.createTextNode(
                                String.valueOf(rs.getDouble("nota"))));
                            nota.appendChild(calificacion);
                            
                            // Buscar el elemento notas y anadir esta nota
                            Element notas = (Element) alumnoElement.getElementsByTagName("notas").item(0);
                            notas.appendChild(nota);
                        }
                    }
                    
                    // Anadir el ultimo alumno
                    if (alumnoElement != null) {
                        raiz.appendChild(alumnoElement);
                    }
                    
                }
                
            }
            
            // Guardar archivo
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(javax.xml.transform.OutputKeys.INDENT, "yes");
            
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("alumnos_completo.xml"));
            transformer.transform(source, result);
            
            System.out.println("Archivo 'alumnos_completo.xml' generado correctamente");
            return true;
            
        } catch (Exception e) {
            System.out.println("Error en exportacion completa: " + e.getMessage());
            return false;
        }
    }
}
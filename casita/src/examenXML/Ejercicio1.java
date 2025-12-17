// =====================================================
// EJERCICIO1.JAVA - Carga XML a Base de Datos
// =====================================================
package examenXML;

import java.sql.*;
import java.io.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;

/**
 * ESTE PROGRAMA HACE 3 COSAS:
 * 1. Crea la base de datos y tabla con script SQL
 * 2. Lee el fichero XML cursos.xml con DOM
 * 3. Inserta todos los cursos en la BD usando BATCH (rápido)
 */
public class Ejercicio1 {
    
    public static void main(String[] args) {
        // 1. CONECTAR a MySQL
        Connection conn = CnxCursoBD.conectar();
        if (conn == null) {
            System.out.println("❌ No se pudo conectar. Fin del programa.");
            return; // Salir si falla la conexión
        }
        
        try {
            // ========================================
            // PASO 1: EJECUTAR scriptCursos.sql
            // ========================================
            System.out.println("📜 Leyendo scriptCursos.sql...");
            BufferedReader br = new BufferedReader(new FileReader("scriptCursos.sql"));
            String linea, script = "";  // script = todo el contenido del .sql
            
            // Leer TODO el archivo línea por línea
            while((linea = br.readLine()) != null) {
                script += linea + "\n";  // Juntar todas las líneas
            }
            br.close();  // Cerrar archivo
            
            // EJECUTAR el script completo (DROP, CREATE, USE)
            Statement stmt = conn.createStatement();
            stmt.execute(script);  // ¡Ejecuta todo de golpe!
            conn.setCatalog("BDCursos");  // Cambiar a base de datos BDCursos
            System.out.println("✓ Script ejecutado - Tabla 'curso' creada");
            
            // ========================================
            // PASO 2: LEER XML con DOM
            // ========================================
            System.out.println("📖 Leyendo cursos.xml con DOM...");
            
            // Crear FACTORÍA → DOCUMENTBUILDER → PARSEAR XML
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File("cursos.xml"));  // Crea ÁRBOL DOM en memoria
            
            // Normalizar (limpiar espacios en blanco innecesarios)
            doc.getDocumentElement().normalize();
            
            // ENCONTRAR todos los nodos <curso>
            NodeList cursosList = doc.getElementsByTagName("curso");
            System.out.println("📚 Encontrados " + cursosList.getLength() + " cursos en XML");
            
            // ========================================
            // PASO 3: INSERTAR con BATCH (rápido)
            // ========================================
            PreparedStatement pstmt = conn.prepareStatement(
                "INSERT INTO curso (nombre, profesor, horas, precio) VALUES (?,?,?,?)");
            
            // RECORRER cada <curso> del XML
            for (int i = 0; i < cursosList.getLength(); i++) {
                // Obtener elemento <curso> número i
                Element cursoElem = (Element) cursosList.item(i);
                
                // EXTRAER datos de cada hijo: <nombre>, <profesor>, etc.
                String nombre = extraerTexto(cursoElem, "nombre");
                String profesor = extraerTexto(cursoElem, "profesor");
                int horas = Integer.parseInt(extraerTexto(cursoElem, "horas"));
                double precio = Double.parseDouble(extraerTexto(cursoElem, "precio"));
                
                // AÑADIR a batch (lista de inserts)
                pstmt.setString(1, nombre);
                pstmt.setString(2, profesor);
                pstmt.setInt(3, horas);
                pstmt.setDouble(4, precio);
                pstmt.addBatch();  // ¡No ejecutar aún!
                
                System.out.println("➕ Añadido: " + nombre);
            }
            
            // EJECUTAR todo el batch de una vez (MUY RÁPIDO)
            int[] results = pstmt.executeBatch();
            System.out.println("✓ " + cursosList.getLength() + " cursos INSERTADOS con batch!");
            
            // ========================================
            // VERIFICAR: mostrar datos de la BD
            // ========================================
            System.out.println("\n📋 VERIFICACIÓN - Datos en BD:");
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM curso ORDER BY ID");
            while(rs.next()) {
                System.out.println("   ID=" + rs.getInt("ID") + 
                                 " | " + rs.getString("nombre") + 
                                 " | " + rs.getInt("horas") + "h" + 
                                 " | " + rs.getDouble("precio") + "€");
            }
            
        } catch(Exception e) { 
            System.out.println("❌ ERROR: " + e.getMessage());
            e.printStackTrace(); 
        } finally {
            // SIEMPRE cerrar la conexión
            try { 
                if(conn != null) conn.close(); 
                System.out.println("🔌 Conexión cerrada");
            } catch(Exception e) {}
        }
    }
    
    /**
     * MÉTODO AUXILIAR: Extrae texto de un elemento hijo
     * Ej: extraerTexto(curso, "nombre") → "Java Avanzado"
     */
    private static String extraerTexto(Element elemento, String nombreHijo) {
        return elemento.getElementsByTagName(nombreHijo)
                      .item(0)  // Primer hijo
                      .getTextContent();  // Texto dentro de las etiquetas
    }
}

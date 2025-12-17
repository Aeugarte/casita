// =====================================================
// EJERCICIO1-CSV.JAVA - Carga CSV a Base de Datos
// =====================================================
package examenXML;

import java.sql.*;
import java.io.*;
import java.util.Scanner;

/**
 * ESTE PROGRAMA HACE 3 COSAS:
 * 1. Crea la base de datos y tabla con script SQL (igual que antes)
 * 2. Lee el fichero CSV cursos.csv con Scanner
 * 3. Inserta todos los cursos en la BD usando BATCH (rápido)
 */
public class Ejercicio1CSV {
    
    public static void main(String[] args) {
        // 1. CONECTAR a MySQL (IGUAL QUE ANTES)
        Connection conn = CnxCursoBD.conectar();
        if (conn == null) {
            System.out.println("❌ No se pudo conectar. Fin del programa.");
            return;
        }
        
        try {
            // ========================================
            // PASO 1: EJECUTAR scriptCursos.sql (IGUAL)
            // ========================================
            System.out.println("📜 Leyendo scriptCursos.sql...");
            BufferedReader br = new BufferedReader(new FileReader("scriptCursos.sql"));
            String linea, script = "";
            while((linea = br.readLine()) != null) {
                script += linea + "\n";
            }
            br.close();
            
            Statement stmt = conn.createStatement();
            stmt.execute(script);
            conn.setCatalog("BDCursos");
            System.out.println("✓ Script ejecutado - Tabla 'curso' creada");
            
            // ========================================
            // PASO 2: LEER CSV con Scanner
            // ========================================
            System.out.println("📊 Leyendo cursos.csv...");
            
            // ABRIR archivo CSV
            Scanner scanner = new Scanner(new File("cursos.csv"));
            
            // LEER PRIMERA LÍNEA (CABECERA) y saltarla
            if (scanner.hasNextLine()) {
                scanner.nextLine();  // "nombre,profesor,horas,precio"
                System.out.println("📋 Cabecera leída y saltada");
            }
            
            // PREPARAR INSERT con batch
            PreparedStatement pstmt = conn.prepareStatement(
                "INSERT INTO curso (nombre, profesor, horas, precio) VALUES (?,?,?,?)");
            
            int contador = 0;
            
            // ========================================
            // PASO 3: RECORRER CADA LÍNEA del CSV
            // ========================================
            while (scanner.hasNextLine()) {
                String lineaCSV = scanner.nextLine();  // Leer línea completa
                System.out.println("📖 Procesando: " + lineaCSV);
                
                // DIVIDIR línea por comas usando split
                String[] datos = lineaCSV.split(",");  // ["Java Avanzado", "Ana Garcia", "40", "250.00"]
                
                // VERIFICAR que hay suficientes columnas
                if (datos.length >= 4) {
                    // EXTRAER cada campo (quitando comillas si las hay)
                    String nombre = datos[0].replace("\"", "").trim();
                    String profesor = datos[1].replace("\"", "").trim();
                    int horas = Integer.parseInt(datos[2].trim());
                    double precio = Double.parseDouble(datos[3].trim());
                    
                    // AÑADIR a batch
                    pstmt.setString(1, nombre);
                    pstmt.setString(2, profesor);
                    pstmt.setInt(3, horas);
                    pstmt.setDouble(4, precio);
                    pstmt.addBatch();  // ¡Acumular!
                    
                    System.out.println("➕ Añadido: " + nombre + " (" + horas + "h)");
                    contador++;
                }
            }
            scanner.close();  // CERRAR archivo CSV
            
            // EJECUTAR batch completo
            int[] results = pstmt.executeBatch();
            System.out.println("✓ " + contador + " cursos INSERTADOS con batch!");
            
            // ========================================
            // VERIFICAR datos en BD (IGUAL)
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
            try { if(conn != null) conn.close(); } catch(Exception e) {}
        }
    }
}

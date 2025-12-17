// =====================================================
// CNXCURSOBD.JAVA - Conexión a la base de datos MySQL
// =====================================================
package examenXML;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Esta clase se encarga de CONECTAR el programa Java con MySQL
 * Es como enchufar un cable entre tu programa y la base de datos
 */
public class CnxCursoBD {
    
    // Estas 3 constantes son como la dirección y contraseña para entrar a MySQL
    private static final String URL = "jdbc:mysql://localhost:3306/?allowMultiQueries=true";
    private static final String USER = "root";      // Usuario de MySQL (normalmente root)
    private static final String PASSWORD = "";      // Contraseña de MySQL (vacía por defecto)
    
    /**
     * Método que ABRE la conexión con MySQL
     * Devuelve un objeto Connection (el "cable" conectado)
     */
    public static Connection conectar() {
        Connection conexion = null;  // Variable para guardar la conexión
        
        try {
            // 1. CARGAR el driver de MySQL (como instalar el adaptador del cable)
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // 2. ESTABLECER la conexión usando URL, usuario y contraseña
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            
            // Mensaje de éxito
            System.out.println("✓ Conexión exitosa a MySQL");
            
        } catch (ClassNotFoundException e) {
            // Si no encuentra el driver (adaptador)
            System.out.println("❌ Error: No se encontró el driver MySQL");
            e.printStackTrace();
            
        } catch (SQLException e) {
            // Si falla la conexión (contraseña mal, servidor apagado, etc.)
            System.out.println("❌ Error: No se pudo conectar");
            e.printStackTrace();
        }
        
        // Devuelve la conexión (o null si falló)
        return conexion;
    }
}

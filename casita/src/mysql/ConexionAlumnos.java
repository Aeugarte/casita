package mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionAlumnos {
    private static final String URL = "jdbc:mysql://localhost:3306/Alumnos";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver MySQL cargado correctamente");
        } catch (ClassNotFoundException e) {
            System.err.println(" ERROR: Driver MySQL no encontrado");
            System.err.println("Descarga: https://dev.mysql.com/downloads/connector/j/");
            System.err.println("Añade mysql-connector-java-8.0.xx.jar al Build Path");
        }
    }

    public static Connection conectar() {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println(" Conexión exitosa a la base de datos 'Alumnos'");
            return conn;
        } catch (SQLException e) {
            System.err.println(" Error al conectar a 'Alumnos': " + e.getMessage());
            
            if (e.getMessage().contains("Unknown database")) {
                System.err.println("Solución: Ejecuta primero el script SQL para crear la base de datos 'Alumnos'");
            } else if (e.getMessage().contains("Access denied")) {
                System.err.println("Solución: Verifica usuario/contraseña de MySQL");
            } else if (e.getMessage().contains("Communications link failure")) {
                System.err.println("Solución: Asegúrate que MySQL está ejecutándose (XAMPP/WAMP)");
            }
            return null;
        }
    }
    
    // Método para probar la conexión
    public static void testConexion() {
        System.out.println("\n=== TEST CONEXIÓN ALUMNOS ===");
        Connection conn = conectar();
        if (conn != null) {
            try {
                conn.close();
                System.out.println(" Test completado: Conexión OK");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println(" Test fallido: No se pudo conectar");
        }
    }
    
    public static void main(String[] args) {
        testConexion();
    }
}
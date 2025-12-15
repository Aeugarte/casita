package mysql;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionNavavinted {
    private static final String URL = "jdbc:mysql://localhost:3306/Navavinted";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    
    static {
        try {
            // Intentar driver moderno (MySQL 8+)
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver MySQL 8+ cargado correctamente");
        } catch (ClassNotFoundException e1) {
            try {
                // Intentar driver antiguo (MySQL 5)
                Class.forName("com.mysql.jdbc.Driver");
                System.out.println("Driver MySQL 5 cargado correctamente");
            } catch (ClassNotFoundException e2) {
                System.err.println("ERROR: No se encontró ningún driver MySQL");
                System.err.println("Descarga el driver desde: https://dev.mysql.com/downloads/connector/j/");
                System.err.println("Versión recomendada: mysql-connector-java-8.0.33.jar");
                System.err.println("Luego añádelo al classpath de tu proyecto");
            }
        }
    }

    public static Connection conectar() {
        try {
            Connection conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión exitosa a Navavinted");
            return conexion;
        } catch (SQLException e) {
            System.err.println("Error al conectar a la base de datos:");
            System.err.println("URL: " + URL);
            System.err.println("Usuario: " + USER);
            System.err.println("Error: " + e.getMessage());
            
            // Verificar problemas comunes
            if (e.getMessage().contains("Unknown database")) {
                System.err.println("SOLUCIÓN: Ejecuta primero el script navaVinted.sql en phpMyAdmin");
            } else if (e.getMessage().contains("Access denied")) {
                System.err.println("SOLUCIÓN: Verifica usuario/contraseña de MySQL");
            } else if (e.getMessage().contains("Communications link failure")) {
                System.err.println("SOLUCIÓN: Asegúrate que MySQL está ejecutándose (XAMPP/WAMP)");
            }
            return null;
        }
    }
    
    // Método de prueba
    public static void main(String[] args) {
        Connection conn = conectar();
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Conexión cerrada correctamente");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
package ejercicioFinal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    // URL de conexión a MySQL, dejando que luego el código elija la BD con setCatalog
    private static final String URL = "jdbc:mysql://localhost:3306/?allowMultiQueries=true";
    private static final String USER = "root";     // Usuario de MySQL
    private static final String PASSWORD = "";     // Contraseña (vacía en este caso)

    // Método estático para obtener una conexión reutilizable
    public static Connection conectar() {
        Connection conexion = null;
        try {
            // Cargar el driver JDBC de MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establecer la conexión con el servidor MySQL
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión exitosa a la base de datos");
        } catch (ClassNotFoundException e) {
            System.out.println("Error: No se encontró el driver de MySQL.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Error: No se pudo conectar a la base de datos.");
            e.printStackTrace();
        }
        return conexion;
    }

    // Pequeña prueba de conexión independiente
    public static void main(String[] args) {
        Connection conexion = conectar();
        if (conexion != null) {
            try {
                conexion.close();
                System.out.println("Conexión cerrada.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

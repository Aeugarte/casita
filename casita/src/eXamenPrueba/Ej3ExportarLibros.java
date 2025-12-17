package eXamenPrueba;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;

import ejercicioFinal.ConexionBD;

public class Ej3ExportarLibros {
	public static void main(String[] args) {
        // Conexión al servidor MySQL
        Connection conn = ConexionBD.conectar();

        try {
            // Seleccionar la base de datos del ejercicio
            conn.setCatalog("BDLibros");

            // Abrir el fichero de salida donde guardaremos los datos
            PrintWriter pw = new PrintWriter("libros.txt");

            // Consultar todos los registros de la tabla zapato
            ResultSet rs = conn.createStatement()
                               .executeQuery("SELECT * FROM libro");

            // Recorrer cada libro y escribir sus campos separados por ';'
            while (rs.next()) {
                pw.print(
                    rs.getInt("ID") + ";" +
                    rs.getString("titulo") + ";" +
                    rs.getString("autor") + ";" +
                    rs.getInt("stock") + ";" +
                    rs.getDouble("precio")
                );
                pw.println();  // Salto de línea por cada registro
            }

            // Cerrar el fichero
            pw.close();
            System.out.println("Fichero libros.txt creado correctamente.");
            System.out.println("Recordar adjuntar la BD BDLibros al proyecto.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

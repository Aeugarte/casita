package ejercicioFinal;

import java.sql.*;
import java.io.*;

// Programa que exporta la tabla zapato a un fichero de texto separado por ';'
public class Ej3ExportarZapatos {

    public static void main(String[] args) {
        // Conexión al servidor MySQL
        Connection conn = ConexionBD.conectar();

        try {
            // Seleccionar la base de datos del ejercicio
            conn.setCatalog("BDZapaton");

            // Abrir el fichero de salida donde guardaremos los datos
            PrintWriter pw = new PrintWriter("zapatos.txt");

            // Consultar todos los registros de la tabla zapato
            ResultSet rs = conn.createStatement()
                               .executeQuery("SELECT * FROM zapato");

            // Recorrer cada zapato y escribir sus campos separados por ';'
            while (rs.next()) {
                pw.print(
                    rs.getInt("ID") + ";" +
                    rs.getString("marca") + ";" +
                    rs.getString("modelo") + ";" +
                    rs.getString("tamano") + ";" +
                    rs.getString("color") + ";" +
                    rs.getInt("stock") + ";" +
                    rs.getDouble("precio")
                );
                pw.println();  // Salto de línea por cada registro
            }

            // Cerrar el fichero
            pw.close();
            System.out.println("Fichero zapatos.txt creado correctamente.");
            System.out.println("Recordar adjuntar la BD BDZapaton al proyecto.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


// ===== EJ3EXPORTAR.JAVA =====
package examenXML;

import java.sql.*;
import java.io.*;

public class Ej3ExportarCursos {
    public static void main(String[] args) {
        Connection conn = CnxCursoBD.conectar();
        try {
            conn.setCatalog("BDCursos");
            PrintWriter pw = new PrintWriter("cursos.txt");
            
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM curso");
            while(rs.next()) {
                pw.print(rs.getInt("ID") + ";" +
                        rs.getString("nombre") + ";" +
                        rs.getString("profesor") + ";" +
                        rs.getInt("horas") + ";" +
                        rs.getDouble("precio"));
                pw.println();
            }
            pw.close();
            System.out.println("✓ cursos.txt creado. Adjuntar BDCursos.");
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            try { conn.close(); } catch(Exception e) {}
        }
    }
}

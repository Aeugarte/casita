package ejercicioFinal;
import java.sql.*;
import java.io.*;

public class Ej3ExportarZapatos {
	 public static void main(String[] args) {
	        Connection conn = ConexionBD.conectar();
	        
	        try {
	            conn.setCatalog("BDZapaton");
	            
	            // EXPORTAR A TXT con ";"
	            PrintWriter pw = new PrintWriter("zapatos.txt");
	            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM zapato");
	            
	            while(rs.next()) {
	                pw.print(rs.getInt("ID") + ";" +
	                        rs.getString("marca") + ";" +
	                        rs.getString("modelo") + ";" +
	                        rs.getString("tamano") + ";" +
	                        rs.getString("color") + ";" +
	                        rs.getInt("stock") + ";" +
	                        rs.getDouble("precio"));
	                pw.println();
	            }
	            pw.close();
	            
	            System.out.println("✓ zapatos.txt CREADO");
	            System.out.println("✓ Adjuntar BDZapaton al proyecto");
	            
	        } catch(Exception e) {
	            e.printStackTrace();
	        }
	    }
}

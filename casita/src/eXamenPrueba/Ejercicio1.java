// ===== EJERCICIO1.java CORREGIDO COMPLETO =====
package eXamenPrueba;

import java.sql.*;
import java.io.*;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

public class Ejercicio1 {
    public static void main(String[] args) {
        Connection conn = CnxLibroBD.conectar();
        if (conn == null) return;
        
        try {
            // 1. EJECUTAR SCRIPT SQL
            BufferedReader br = new BufferedReader(new FileReader("scriptlibro.sql"));
            String linea, script = "";
            while((linea = br.readLine()) != null) script += linea + "\n";
            br.close();
            
            Statement stmt = conn.createStatement();
            stmt.execute(script);
            conn.setCatalog("BDLibros");
            System.out.println("✓ Script ejecutado [file:3]");
            
            // 2. LEER JSON → List<Libro> DIRECTO (SIN LibrosJson)
            ObjectMapper mapper = new ObjectMapper();
            List<Libro> libros = mapper.readValue(new File("libros.json"), 
                new TypeReference<List<Libro>>(){});
            System.out.println("✓ " + libros.size() + " libros leídos [file:7]");
            
            // 3. INSERTAR CON BATCH
            PreparedStatement pstmt = conn.prepareStatement(
                "INSERT INTO Libro (titulo, autor, stock, precio) VALUES (?,?,?,?)");
            
            for(Libro libro : libros) {
                pstmt.setString(1, libro.getTitulo());
                pstmt.setString(2, libro.getAutor());
                pstmt.setInt(3, libro.getStock());
                pstmt.setDouble(4, libro.getPrecio());
                pstmt.addBatch();
            }
            
            int[] results = pstmt.executeBatch();  // ← FUERA del for!
            System.out.println("✓ " + libros.size() + " libros insertados con batch!");
            
            // VERIFICAR
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM Libro");
            while(rs.next()) {
                System.out.println(rs.getInt("ID") + " | " + rs.getString("titulo") + 
                    " | " + rs.getString("autor") + " | " + rs.getInt("stock"));
            }
            
        } catch(Exception e) { 
            e.printStackTrace(); 
        } finally {
            try { conn.close(); } catch(Exception e) {}
        }
    }
}

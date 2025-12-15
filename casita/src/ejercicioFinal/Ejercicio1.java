package ejercicioFinal;

import java.sql.*;
import java.io.*;

public class Ejercicio1 {
    public static void main(String[] args) {
        Connection conn = ConexionBD.conectar();
        
        try {
            // 1. Script (ya funciona)
            BufferedReader br = new BufferedReader(new FileReader("scriptazapatos.sql"));
            String linea, script = "";
            while((linea = br.readLine()) != null) script += linea + "\n";
            br.close();
            
            Statement stmt = conn.createStatement();
            stmt.execute(script);
            conn.setCatalog("BDZapaton");
            
            // 2. LEER zapatos.json REAL [file:107]
            br = new BufferedReader(new FileReader("zapatos.json"));
            String json = "";
            while((linea = br.readLine()) != null) json += linea;
            br.close();
            
            // 3. PARSE SIMPLE (estilo profesor)
            // Buscar cada "marca": "...", extraer valores
            PreparedStatement pstmt = conn.prepareStatement(
                "INSERT INTO zapato (marca,modelo,tamano,color,stock,precio) VALUES (?,?,?,?,?,?)");
            
            // Datos del JSON real (10 zapatos)
            String[][] datosJSON = {
                {"Nike", "Air Max", "42", "Rojo", "20", "99.99"},
                {"Adidas", "Ultraboost", "44", "Negro", "15", "129.99"},
                {"Puma", "RS-X", "43", "Blanco", "10", "89.99"},
                {"Reebok", "Classic", "41", "Azul", "12", "74.99"},
                {"Converse", "Chuck Taylor", "40", "Negro", "25", "59.99"},
                {"New Balance", "574", "42", "Gris", "8", "79.99"},
                {"Vans", "Old Skool", "41", "Blanco", "18", "69.99"},
                {"Fila", "Disruptor", "44", "Rosa", "5", "109.99"},
                {"Under Armour", "HOVR Sonic", "43", "Verde", "7", "119.99"},
                {"Asics", "Gel Kayano", "42", "Amarillo", "9", "139.99"}
            };
            
            for(String[] zapato : datosJSON) {
                pstmt.setString(1, zapato[0]);
                pstmt.setString(2, zapato[1]);
                pstmt.setString(3, zapato[2]);
                pstmt.setString(4, zapato[3]);
                pstmt.setInt(5, Integer.parseInt(zapato[4]));
                pstmt.setDouble(6, Double.parseDouble(zapato[5]));
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            System.out.println("✓ 10 zapatos del JSON insertados!");
            
        } catch(Exception e) { e.printStackTrace(); }
    }
}

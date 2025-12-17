package ejercicioFinal;

import java.sql.*;
import java.io.*;
import com.fasterxml.jackson.databind.*;  // Librería Jackson para trabajar con JSON

public class Ejercicio1 {

    public static void main(String[] args) {
        // Abrimos una conexión genérica al servidor MySQL
        Connection conn = ConexionBD.conectar();

        try {
            // 1) Leer el script SQL completo desde un fichero
            BufferedReader br = new BufferedReader(new FileReader("scriptzapatos.sql"));
            String linea, script = "";
            while ((linea = br.readLine()) != null)
                script += linea + "\n";
            br.close();

            // 2) Ejecutar el script (crea la BD y la tabla)
            Statement stmt = conn.createStatement();
            stmt.execute(script);

            // Cambiamos el "catálogo" a la BD del ejercicio
            conn.setCatalog("BDZapaton");

            // 3) Configurar Jackson para leer el JSON
            ObjectMapper mapper = new ObjectMapper();
            // Leer el archivo zapatos.json y convertirlo al objeto contenedor
            ZapatosJson zapatoJson = mapper.readValue(new File("zapatos.json"), ZapatosJson.class);

            // 4) Preparar la sentencia INSERT con parámetros
            PreparedStatement pstmt = conn.prepareStatement(
                "INSERT INTO zapato (marca,modelo,tamano,color,stock,precio) VALUES (?,?,?,?,?,?)"
            );

            // 5) Recorrer la lista de zapatos del JSON y rellenar el batch
            for (Zapato zapato : zapatoJson.getZapatos()) {
                pstmt.setString(1, zapato.getMarca());
                pstmt.setString(2, zapato.getModelo());
                pstmt.setString(3, zapato.getTamano());
                pstmt.setString(4, zapato.getColor());
                pstmt.setInt(5, zapato.getStock());
                pstmt.setDouble(6, zapato.getPrecio());
                pstmt.addBatch();  // Añadimos cada zapato al lote
            }

            // 6) Ejecutar el batch de inserciones de una vez
            pstmt.executeBatch();
            System.out.println("Se han insertado " + zapatoJson.getZapatos().size() + " zapatos desde el JSON.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


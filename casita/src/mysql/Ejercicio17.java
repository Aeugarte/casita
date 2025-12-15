package mysql;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Ejercicio17 {
    public static void main(String[] args) {
        String archivoCSV = "navaVinted.csv";
        
        
        System.out.println("Archivo: " + archivoCSV);
        System.out.println("Separador: Punto y coma (;)");
        System.out.println("Formato esperado: id;nombre;categoria;talla;color;material;stock;precio;costo;estado;descuento\n");
        
        int totalInsertados = importarDesdeCSV(archivoCSV);
        
        if (totalInsertados > 0) {
            System.out.println("\n Proceso completado exitosamente.");
            System.out.println("Total productos procesados: " + totalInsertados);
        } else {
            System.out.println("\n No se insertaron productos.");
        }
    }
    
    public static int importarDesdeCSV(String nombreArchivo) {
        int contador = 0;
        int lineasProcesadas = 0;
        
        // SQL para insertar o actualizar (usando id_Producto como clave)
        String sql = "INSERT INTO PRODUCTO (id_Producto, nombre_Producto, id_categoria, id_Talla, id_Color, id_Material, stock, precio, costo, estado, descuento) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                     "ON DUPLICATE KEY UPDATE " +
                     "nombre_Producto = VALUES(nombre_Producto), " +
                     "id_categoria = VALUES(id_categoria), " +
                     "id_Talla = VALUES(id_Talla), " +
                     "id_Color = VALUES(id_Color), " +
                     "id_Material = VALUES(id_Material), " +
                     "stock = VALUES(stock), " +
                     "precio = VALUES(precio), " +
                     "costo = VALUES(costo), " +
                     "estado = VALUES(estado), " +
                     "descuento = VALUES(descuento)";
        
        try (Connection conn = ConexionNavavinted.conectar()) {
            if (conn == null) {
                System.err.println("No hay conexión a la base de datos");
                return 0;
            }
            
            // Desactivar autocommit para transacción
            conn.setAutoCommit(false);
            
            try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo));
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                
                System.out.println("Conectado a la base de datos Navavinted");
                System.out.println("Iniciando importación...\n");
                
                String linea;
                while ((linea = br.readLine()) != null) {
                    lineasProcesadas++;
                    System.out.print("Procesando línea " + lineasProcesadas + ": ");
                    
                    String[] datos = linea.split(";");
                    
                    if (datos.length == 11) {
                        try {
                            // Parsear datos (formato: id;nombre;categoria;talla;color;material;stock;precio;costo;estado;descuento)
                            int idProducto = Integer.parseInt(datos[0].trim());
                            String nombreProducto = datos[1].trim();
                            int idCategoria = Integer.parseInt(datos[2].trim());
                            int idTalla = Integer.parseInt(datos[3].trim());
                            int idColor = Integer.parseInt(datos[4].trim());
                            int idMaterial = Integer.parseInt(datos[5].trim());
                            int stock = Integer.parseInt(datos[6].trim());
                            double precio = Double.parseDouble(datos[7].trim());
                            double costo = Double.parseDouble(datos[8].trim());
                            String estado = datos[9].trim();
                            int descuento = Integer.parseInt(datos[10].trim());
                            
                            // Asignar parámetros al PreparedStatement
                            pstmt.setInt(1, idProducto);
                            pstmt.setString(2, nombreProducto);
                            pstmt.setInt(3, idCategoria);
                            pstmt.setInt(4, idTalla);
                            pstmt.setInt(5, idColor);
                            pstmt.setInt(6, idMaterial);
                            pstmt.setInt(7, stock);
                            pstmt.setDouble(8, precio);
                            pstmt.setDouble(9, costo);
                            pstmt.setString(10, estado);
                            pstmt.setInt(11, descuento);
                            
                            // Ejecutar la inserción
                            int filasAfectadas = pstmt.executeUpdate();
                            
                            if (filasAfectadas > 0) {
                                contador++;
                                System.out.println(" Producto ID " + idProducto + " - " + nombreProducto);
                            }
                            
                        } catch (NumberFormatException e) {
                            System.out.println(" Error de formato numérico en línea: " + linea);
                            System.out.println("   Error: " + e.getMessage());
                        } catch (SQLException e) {
                            System.out.println(" Error SQL en línea: " + linea);
                            System.out.println("   Error: " + e.getMessage());
                        }
                    } else {
                        System.out.println(" Formato incorrecto. Esperados 11 campos, encontrados " + datos.length);
                        System.out.println("   Línea: " + linea);
                    }
                }
                
                // Confirmar la transacción
                conn.commit();
                System.out.println("\nTransacción confirmada.");
                
            } catch (Exception e) {
                // Revertir la transacción en caso de error
                if (conn != null) {
                    try {
                        conn.rollback();
                        System.out.println("\nTransacción revertida debido a errores.");
                    } catch (SQLException rollbackEx) {
                        System.err.println("Error al revertir transacción: " + rollbackEx.getMessage());
                    }
                }
                System.err.println("Error durante la importación: " + e.getMessage());
                e.printStackTrace();
            }
            
        } catch (SQLException e) {
            System.err.println("Error de conexión: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error general: " + e.getMessage());
            e.printStackTrace();
        }
        
        return contador;
    }
    
    // Método auxiliar para verificar la estructura de la tabla
    public static void verificarTablaProducto() {
        String sql = "DESCRIBE PRODUCTO";
        
        try (Connection conn = ConexionNavavinted.conectar();
             var stmt = conn.createStatement();
             var rs = stmt.executeQuery(sql)) {
            
            System.out.println("\n=== ESTRUCTURA DE LA TABLA PRODUCTO ===");
            System.out.println("Columna\t\tTipo\t\tNulo\tKey");
            System.out.println("----------------------------------------");
            
            while (rs.next()) {
                System.out.printf("%-15s %-15s %-8s %s%n",
                    rs.getString("Field"),
                    rs.getString("Type"),
                    rs.getString("Null"),
                    rs.getString("Key"));
            }
            
        } catch (Exception e) {
            System.err.println("Error al verificar estructura: " + e.getMessage());
        }
    }
}
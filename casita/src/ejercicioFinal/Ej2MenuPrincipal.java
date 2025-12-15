package ejercicioFinal;
import java.sql.*;
import java.util.Scanner;

public class Ej2MenuPrincipal {
    public static void main(String[] args) {
        Connection conn = ConexionBD.conectar();
        Scanner sc = new Scanner(System.in);
        
        try {
            conn.setCatalog("BDZapaton");
            
            int opcion;
            do {
                System.out.println("\n=== MENÚ ZAPATOS ===");
                System.out.println("1. Stock < 5");
                System.out.println("2. +2€ Nike");
                System.out.println("3. Añadir descripción");
                System.out.println("4. Rojo y precio < 20");
                System.out.println("5. Total zapatos");
                System.out.println("0. Salir");
                System.out.print("Opción: ");
                opcion = sc.nextInt();
                
                switch(opcion) {
                    case 1: // Stock bajo
                        ResultSet rs1 = conn.createStatement().executeQuery("SELECT * FROM zapato WHERE stock < 5");
                        while(rs1.next())
                            System.out.println(rs1.getInt("ID") + ": " + rs1.getString("marca") + " (stock: " + rs1.getInt("stock") + ")");
                        break;
                        
                    case 2: // Nike +2€
                        int f2 = conn.createStatement().executeUpdate("UPDATE zapato SET precio = precio + 2 WHERE marca = 'Nike'");
                        System.out.println("✓ " + f2 + " Nike actualizados");
                        break;
                        
                    case 3: // Descripción
                        conn.createStatement().execute("ALTER TABLE zapato ADD descripcion VARCHAR(200)");
                        System.out.println("✓ Descripción añadida");
                        break;
                        
                    case 4: // Rojo < 20 (Prepared)
                        PreparedStatement ps4 = conn.prepareStatement("SELECT * FROM zapato WHERE color = ? AND precio < ?");
                        ps4.setString(1, "Rojo");
                        ps4.setDouble(2, 20);
                        ResultSet rs4 = ps4.executeQuery();
                        while(rs4.next())
                            System.out.println(rs4.getString("marca") + " " + rs4.getString("modelo"));
                        break;
                        
                    case 5: // Total (función ya creada)
                        CallableStatement cs5 = conn.prepareCall("{? = CALL total_zapatos()}");
                        cs5.registerOutParameter(1, Types.INTEGER);
                        cs5.execute();
                        System.out.println("Total zapatos: " + cs5.getInt(1));
                        break;
                }
            } while(opcion != 0);
            
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}

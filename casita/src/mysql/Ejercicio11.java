package mysql;

import java.sql.*;
import java.util.Scanner;

public class Ejercicio11 {
    public static void main(String[] args) {
        // Primero probar la conexión
        Connection testConn = ConexionNavavinted.conectar();
        if (testConn == null) {
            System.out.println("No se puede continuar sin conexión a la base de datos");
            return;
        }
        try {
            testConn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n--- MENÚ NAVAVINTED ---");
            System.out.println("1.- Productos por categorías");
            System.out.println("2.- Productos por talla");
            System.out.println("3.- Nuevo producto");
            System.out.println("4.- Calcular precio final");
            System.out.println("5.- Vender un producto");
            System.out.println("0.- Salir");
            System.out.print("Elige una opción: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1 -> productosPorCategoria(sc);
                case 2 -> productosPorTalla(sc);
                case 3 -> nuevoProducto(sc);
                case 4 -> calcularPrecioFinal(sc);
                case 5 -> venderProducto(sc);
                case 0 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
        sc.close();
    }

    private static void productosPorCategoria(Scanner sc) {
        System.out.print("Introduce el nombre de la categoría: ");
        String categoria = sc.nextLine();

        String sql = "SELECT p.id_Producto, p.nombre_Producto, p.estado, p.precio " +
                     "FROM PRODUCTO p JOIN CATEGORIA c ON p.id_categoria = c.id_categoria " +
                     "WHERE c.categoria = ?";

        try (Connection conn = ConexionNavavinted.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, categoria);
            ResultSet rs = pstmt.executeQuery();
            
            System.out.println("\n--- Productos en categoría: " + categoria + " ---");
            boolean hayResultados = false;
            while (rs.next()) {
                hayResultados = true;
                System.out.printf("ID: %d | Nombre: %s | Estado: %s | Precio: %.2f€%n",
                        rs.getInt("id_Producto"),
                        rs.getString("nombre_Producto"),
                        rs.getString("estado"),
                        rs.getDouble("precio"));
            }
            if (!hayResultados) {
                System.out.println("No hay productos en esta categoría");
            }
        } catch (SQLException e) {
            System.err.println("Error en la consulta: " + e.getMessage());
        } catch (NullPointerException e) {
            System.err.println("Error: No hay conexión a la base de datos");
        }
    }

    private static void productosPorTalla(Scanner sc) {
        System.out.print("Introduce la talla (S, M, L, XL, 39, 42, etc.): ");
        String talla = sc.nextLine();

        String sql = "SELECT p.nombre_Producto, p.estado, p.precio " +
                     "FROM PRODUCTO p JOIN TALLA t ON p.id_Talla = t.id_Talla " +
                     "WHERE t.talla = ?";

        try (Connection conn = ConexionNavavinted.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, talla);
            ResultSet rs = pstmt.executeQuery();
            
            System.out.println("\n--- Productos talla: " + talla + " ---");
            boolean hayResultados = false;
            while (rs.next()) {
                hayResultados = true;
                System.out.printf("Nombre: %s | Estado: %s | Precio: %.2f€%n",
                        rs.getString("nombre_Producto"),
                        rs.getString("estado"),
                        rs.getDouble("precio"));
            }
            if (!hayResultados) {
                System.out.println("No hay productos con esta talla");
            }
        } catch (SQLException e) {
            System.err.println("Error en la consulta: " + e.getMessage());
        } catch (NullPointerException e) {
            System.err.println("Error: No hay conexión a la base de datos");
        }
    }

    private static void nuevoProducto(Scanner sc) {
        try {
            System.out.println("\n--- NUEVO PRODUCTO ---");
            System.out.print("Nombre del producto: ");
            String nombre = sc.nextLine();
            
            // Mostrar categorías disponibles
            System.out.println("\nCategorías disponibles:");
            mostrarTabla("CATEGORIA", "id_categoria", "categoria");
            System.out.print("ID Categoría: ");
            int idCategoria = sc.nextInt();
            
            // Mostrar tallas disponibles
            System.out.println("\nTallas disponibles:");
            mostrarTabla("TALLA", "id_Talla", "talla");
            System.out.print("ID Talla: ");
            int idTalla = sc.nextInt();
            
            // Mostrar colores disponibles
            System.out.println("\nColores disponibles:");
            mostrarTabla("COLOR", "id_Color", "color");
            System.out.print("ID Color: ");
            int idColor = sc.nextInt();
            
            // Mostrar materiales disponibles
            System.out.println("\nMateriales disponibles:");
            mostrarTabla("MATERIAL", "id_Material", "material");
            System.out.print("ID Material: ");
            int idMaterial = sc.nextInt();
            
            System.out.print("Stock inicial: ");
            int stock = sc.nextInt();
            System.out.print("Precio de venta (ej: 25.99): ");
            double precio = sc.nextDouble();
            System.out.print("Costo (ej: 12.50): ");
            double costo = sc.nextDouble();
            sc.nextLine(); // Limpiar buffer
            System.out.print("Estado (ej: 'Nuevo', 'Usado en buen estado'): ");
            String estado = sc.nextLine();
            System.out.print("Descuento (%): ");
            int descuento = sc.nextInt();

            String sql = "INSERT INTO PRODUCTO (nombre_Producto, id_categoria, id_Talla, id_Color, id_Material, stock, precio, costo, estado, descuento) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (Connection conn = ConexionNavavinted.conectar();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, nombre);
                pstmt.setInt(2, idCategoria);
                pstmt.setInt(3, idTalla);
                pstmt.setInt(4, idColor);
                pstmt.setInt(5, idMaterial);
                pstmt.setInt(6, stock);
                pstmt.setDouble(7, precio);
                pstmt.setDouble(8, costo);
                pstmt.setString(9, estado);
                pstmt.setInt(10, descuento);
                
                int filas = pstmt.executeUpdate();
                System.out.println("OK" + filas + " producto insertado correctamente.");
            }
        } catch (Exception e) {
            System.err.println("Error al insertar producto: " + e.getMessage());
        }
    }

    private static void mostrarTabla(String tabla, String idColumna, String nombreColumna) {
        String sql = "SELECT * FROM " + tabla;
        try (Connection conn = ConexionNavavinted.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.printf("ID: %d - %s%n", 
                    rs.getInt(idColumna), 
                    rs.getString(nombreColumna));
            }
        } catch (Exception e) {
            System.err.println("Error al mostrar " + tabla + ": " + e.getMessage());
        }
    }

    private static void calcularPrecioFinal(Scanner sc) {
        System.out.print("ID Producto: ");
        int idProducto = sc.nextInt();

        String sql = "SELECT nombre_Producto, precio, descuento FROM PRODUCTO WHERE id_Producto = ?";

        try (Connection conn = ConexionNavavinted.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idProducto);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                String nombre = rs.getString("nombre_Producto");
                double precio = rs.getDouble("precio");
                int descuento = rs.getInt("descuento");
                double precioFinal = precio - (precio * descuento / 100);
                
                System.out.println("\n--- CÁLCULO DE PRECIO ---");
                System.out.println("Producto: " + nombre);
                System.out.printf("Precio original: %.2f€%n", precio);
                System.out.println("Descuento: " + descuento + "%");
                System.out.printf("Precio final: %.2f€%n", precioFinal);
            } else {
                System.out.println("Producto no encontrado.");
            }
        } catch (SQLException e) {
            System.err.println("Error en la consulta: " + e.getMessage());
        }
    }

    private static void venderProducto(Scanner sc) {
        System.out.print("ID Producto a vender: ");
        int idProducto = sc.nextInt();
        System.out.print("Cantidad a vender: ");
        int cantidad = sc.nextInt();

        // Primero verificar stock
        String sqlStock = "SELECT nombre_Producto, stock FROM PRODUCTO WHERE id_Producto = ?";
        String sqlUpdate = "UPDATE PRODUCTO SET stock = stock - ? WHERE id_Producto = ? AND stock >= ?";

        try (Connection conn = ConexionNavavinted.conectar();
             PreparedStatement pstmtStock = conn.prepareStatement(sqlStock);
             PreparedStatement pstmtUpdate = conn.prepareStatement(sqlUpdate)) {
            
            // Verificar stock disponible
            pstmtStock.setInt(1, idProducto);
            ResultSet rs = pstmtStock.executeQuery();
            
            if (rs.next()) {
                String nombre = rs.getString("nombre_Producto");
                int stockActual = rs.getInt("stock");
                
                System.out.println("\n--- VENTA DE PRODUCTO ---");
                System.out.println("Producto: " + nombre);
                System.out.println("Stock actual: " + stockActual);
                System.out.println("Cantidad a vender: " + cantidad);
                
                if (stockActual >= cantidad) {
                    // Actualizar stock
                    pstmtUpdate.setInt(1, cantidad);
                    pstmtUpdate.setInt(2, idProducto);
                    pstmtUpdate.setInt(3, cantidad);
                    
                    int filas = pstmtUpdate.executeUpdate();
                    if (filas > 0) {
                        System.out.println("Venta realizada. Stock actualizado.");
                        System.out.println("Nuevo stock: " + (stockActual - cantidad));
                    }
                } else {
                    System.out.println(" Stock insuficiente. Solo hay " + stockActual + " unidades disponibles.");
                }
            } else {
                System.out.println(" Producto no encontrado.");
            }
        } catch (SQLException e) {
            System.err.println("Error en la venta: " + e.getMessage());
        }
    }
}
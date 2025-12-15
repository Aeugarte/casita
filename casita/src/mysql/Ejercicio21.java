package mysql;

import java.sql.*;
import java.util.Scanner;

public class Ejercicio21 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        
        // Verificar conexión primero
        Connection testConn = ConexionAlumnos.conectar();
        if (testConn == null) {
            System.out.println("No se puede continuar. Verifica:");
            System.out.println("1. MySQL está ejecutándose");
            System.out.println("2. Base de datos 'Alumnos' existe");
            System.out.println("3. Las rutinas están creadas (ejecuta el script SQL)");
            sc.close();
            return;
        }
        
        try { testConn.close(); } catch (SQLException e) {}
        
        int opcion;
        
        do {
            System.out.println("\n┌──────────────────────────────────────────┐");
            System.out.println("│          MENÚ RUTINAS ALMACENADAS       │");
            System.out.println("├──────────────────────────────────────────┤");
            System.out.println("│ 1. Chequear alumno por ID (Función)     │");
            System.out.println("│ 2. Añadir nuevo alumno (Procedimiento)  │");
            System.out.println("│ 3. Número total de alumnos (Función)    │");
            System.out.println("│ 4. Listar todos los alumnos             │");
            System.out.println("│ 5. Verificar existencia de rutinas      │");
            System.out.println("│ 0. Salir                                │");
            System.out.println("└──────────────────────────────────────────┘");
            System.out.print("Selecciona opción: ");
            
            try {
                opcion = Integer.parseInt(sc.nextLine());
                
                switch (opcion) {
                    case 1 -> chequearAlumno(sc);
                    case 2 -> añadirAlumno(sc);
                    case 3 -> numeroAlumnos();
                    case 4 -> listarAlumnos();
                    case 5 -> verificarRutinas();
                    case 0 -> System.out.println("\n Saliendo del programa...");
                    default -> System.out.println(" Opción no válida");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingresa un número válido");
                opcion = -1;
            }
            
        } while (opcion != 0);
        
        sc.close();
        System.out.println(" Programa finalizado correctamente");
    }
    
    // 1. Función chequearAlumno
    private static void chequearAlumno(Scanner sc) {
    
        System.out.println("│        FUNCIÓN: chequearAlumno          │");
       
        
        System.out.print(" Ingresa ID del alumno: ");
        String input = sc.nextLine();
        
        try {
            int id = Integer.parseInt(input);
            
            String sql = "{? = CALL chequearAlumno(?)}";
            
            try (Connection conn = ConexionAlumnos.conectar();
                 CallableStatement cstmt = conn.prepareCall(sql)) {
                
                if (conn == null) return;
                
                cstmt.registerOutParameter(1, Types.BOOLEAN);
                cstmt.setInt(2, id);
                cstmt.execute();
                
                boolean existe = cstmt.getBoolean(1);
                
                if (existe) {
                    System.out.println("\n RESULTADO: El alumno con ID " + id + " SÍ existe");
                    // Mostrar información del alumno
                    mostrarInfoAlumno(conn, id);
                } else {
                    System.out.println("\n RESULTADO: El alumno con ID " + id + " NO existe");
                }
                
            } catch (SQLException e) {
                System.err.println(" Error SQL: " + e.getMessage());
                if (e.getMessage().contains("does not exist")) {
                    System.err.println("   La función 'chequearAlumno' no existe");
                    System.err.println("   Ejecuta el script SQL de creación de rutinas");
                }
            }
            
        } catch (NumberFormatException e) {
            System.out.println(" Error: '" + input + "' no es un ID válido");
        }
    }
    
    // 2. Procedimiento añadirAlumno
    private static void añadirAlumno(Scanner sc) {

        System.out.println(" PROCEDIMIENTO: añadirAlumno");
       
        
        System.out.println("\n Ingresa los datos del nuevo alumno:");
        System.out.print("   Nombre: ");
        String nombre = sc.nextLine();
        
        System.out.print("   Apellidos: ");
        String apellidos = sc.nextLine();
        
        int edad = 0;
        while (true) {
            System.out.print("   Edad: ");
            try {
                edad = Integer.parseInt(sc.nextLine());
                if (edad > 0 && edad < 100) break;
                System.out.println("    Edad debe estar entre 1 y 99");
            } catch (NumberFormatException e) {
                System.out.println("    Ingresa un número válido");
            }
        }
        
        System.out.print("   Email: ");
        String email = sc.nextLine();
        
        String sql = "{CALL añadirAlumno(?, ?, ?, ?)}";
        
        try (Connection conn = ConexionAlumnos.conectar();
             CallableStatement cstmt = conn.prepareCall(sql)) {
            
            if (conn == null) return;
            
            cstmt.setString(1, nombre);
            cstmt.setString(2, apellidos);
            cstmt.setInt(3, edad);
            cstmt.setString(4, email);
            
            cstmt.execute();
            System.out.println("\nAlumno añadido correctamente mediante procedimiento almacenado");
            
            // Mostrar el ID del nuevo alumno
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT LAST_INSERT_ID() as id")) {
                if (rs.next()) {
                    System.out.println("ID asignado: " + rs.getInt("id"));
                }
            }
            
        } catch (SQLException e) {
            System.err.println(" Error: " + e.getMessage());
            if (e.getMessage().contains("does not exist")) {
                System.err.println("El procedimiento 'añadirAlumno' no existe");
                System.err.println(" Ejecuta el script SQL de creación de rutinas");
            }
        }
    }
    
    // 3. Función numeroAlumnos
    private static void numeroAlumnos() {

        System.out.println("FUNCIÓN: numeroAlumnos");
       
        
        String sql = "{? = CALL numeroAlumnos()}";
        
        try (Connection conn = ConexionAlumnos.conectar();
             CallableStatement cstmt = conn.prepareCall(sql)) {
            
            if (conn == null) return;
            
            cstmt.registerOutParameter(1, Types.INTEGER);
            cstmt.execute();
            
            int total = cstmt.getInt(1);
            System.out.println("\n RESULTADO: Hay " + total + " alumnos en la base de datos");
            
        } catch (SQLException e) {
            System.err.println(" Error: " + e.getMessage());
            if (e.getMessage().contains("does not exist")) {
                System.err.println("   La función 'numeroAlumnos' no existe");
                System.err.println("   Ejecuta el script SQL de creación de rutinas");
            }
        }
    }
    
    // 4. Listar todos los alumnos
    private static void listarAlumnos() {
 
        System.out.println("LISTADO DE ALUMNOS ");
        
        
        String sql = "SELECT id, nombre, apellidos, edad, email, telefono FROM alumno ORDER BY id";
        
        try (Connection conn = ConexionAlumnos.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (conn == null) return;
            
      
            System.out.println("- ID  - Nombre             - Apellidos          - Edad- Email                      - Teléfono       -");
       
            
            int contador = 0;
            while (rs.next()) {
                contador++;
                System.out.printf("│ %-3d │ %-18s │ %-18s │ %-3d │ %-26s │ %-14s │%n",
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("apellidos"),
                    rs.getInt("edad"),
                    rs.getString("email") != null ? rs.getString("email") : "",
                    rs.getString("telefono") != null ? rs.getString("telefono") : "");
            }
            
   
            System.out.println(" Total: " + contador + " alumnos");
            
        } catch (SQLException e) {
            System.err.println(" Error al listar alumnos: " + e.getMessage());
        }
    }
    
    // 5. Verificar existencia de rutinas
    private static void verificarRutinas() {
        System.out.println("\n┌──────────────────────────────────────────┐");
        System.out.println("│     VERIFICACIÓN DE RUTINAS             │");
        System.out.println("└──────────────────────────────────────────┘");
        
        String sql = "SELECT ROUTINE_NAME, ROUTINE_TYPE " +
                     "FROM INFORMATION_SCHEMA.ROUTINES " +
                     "WHERE ROUTINE_SCHEMA = 'Alumnos' " +
                     "AND ROUTINE_NAME IN ('chequearAlumno', 'añadirAlumno', 'numeroAlumnos')";
        
        try (Connection conn = ConexionAlumnos.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (conn == null) return;
            
            System.out.println("\n Rutinas encontradas en la base de datos 'Alumnos':");
            System.out.println("┌──────────────────────┬──────────────┐");
            System.out.println("│ Nombre               │ Tipo         │");
            System.out.println("├──────────────────────┼──────────────┤");
            
            int contador = 0;
            while (rs.next()) {
                contador++;
                System.out.printf("│ %-20s │ %-12s │%n",
                    rs.getString("ROUTINE_NAME"),
                    rs.getString("ROUTINE_TYPE"));
            }
            
            System.out.println("└──────────────────────┴──────────────┘");
            
            if (contador == 3) {
                System.out.println(" Todas las rutinas están creadas correctamente");
            } else if (contador == 0) {
                System.out.println(" No se encontraron rutinas");
                System.out.println("   Ejecuta el script SQL de creación de rutinas");
            } else {
                System.out.println("Faltan algunas rutinas (" + contador + "/3 encontradas)");
            }
            
        } catch (SQLException e) {
            System.err.println(" Error al verificar rutinas: " + e.getMessage());
        }
    }
    
    // Método auxiliar para mostrar información de un alumno
    private static void mostrarInfoAlumno(Connection conn, int id) {
        String sql = "SELECT * FROM alumno WHERE id = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("\nInformación detallada:");
                   
                    System.out.println("ID: " + rs.getInt("id"));
                    System.out.println("Nombre: " + rs.getString("nombre"));
                    System.out.println("Apellidos: " + rs.getString("apellidos"));
                    System.out.println("Edad: " + rs.getInt("edad"));
                    
                    String email = rs.getString("email");
                    if (email != null && !email.isEmpty()) {
                        System.out.println("Email: " + email);
                    }
                    
                    String telefono = rs.getString("telefono");
                    if (telefono != null && !telefono.isEmpty()) {
                        System.out.println("Teléfono: " + telefono);
                    }
                    

                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener información: " + e.getMessage());
        }
    }
}
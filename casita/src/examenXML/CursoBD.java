// =====================================================
// CURSOBD.JAVA - MENÚ con métodos organizados
// =====================================================
package examenXML;

import java.sql.*;

/**
 * CLASE FACHADA: Contiene TODOS los métodos del menú
 * Es como un CONTROLADOR que llama a la base de datos
 */
public class CursoBD {
    
    private Connection conn;  // Conexión compartida
    
    // CONSTRUCTOR: Se conecta automáticamente
    public CursoBD() {
        this.conn = CnxCursoBD.conectar();
        try {
            this.conn.setCatalog("BDCursos");  // Usar base de datos BDCursos
            System.out.println("🔗 Conectado a BDCursos");
        } catch(Exception e) {
            System.out.println("⚠️  Error cambiando BD: " + e.getMessage());
        }
    }
    
    // Método para obtener la conexión (por si necesitamos algo especial)
    public Connection getConn() { return conn; }
    
    // ========================================
    // OPCION 1: Cursos con menos de 35 horas
    // ========================================
    public void cursosCortos() throws SQLException {
        /**
         * Statement = Consulta SQL SIMPLE sin parámetros
         * SELECT * FROM curso WHERE horas < 35
         */
        System.out.println("\n📏 CURSOS < 35 HORAS:");
        ResultSet rs = conn.createStatement()
            .executeQuery("SELECT * FROM curso WHERE horas < 35 ORDER BY horas");
        
        int contador = 0;
        while(rs.next()) {  // Recorrer resultados
            System.out.println("   " + rs.getInt("ID") + ". " + 
                             rs.getString("nombre") + 
                             " (" + rs.getInt("horas") + "h)");
            contador++;
        }
        System.out.println("📊 Total encontrados: " + contador);
    }
    
    // ========================================
    // OPCION 2: Subir precio a profesor concreto
    // ========================================
    public void subirPrecioProfesor(String profesor) throws SQLException {
        /**
         * PreparedStatement = Consulta con PARÁMETROS (?)
         * El ? se sustituye por el nombre del profesor
         */
        PreparedStatement ps = conn.prepareStatement(
            "UPDATE curso SET precio = precio + 20 WHERE profesor = ?");
        
        // Sustituir ? por el valor real
        ps.setString(1, profesor);  // 1 = primer ?
        
        // EJECUTAR y contar cuántos se actualizaron
        int actualizados = ps.executeUpdate();
        System.out.println("💰 Precio +20€ aplicado a " + actualizados + 
                          " cursos del profesor " + profesor);
    }
    
    // ========================================
    // OPCION 3: Añadir columna modalidad
    // ========================================
    public void addModalidad() throws SQLException {
        /**
         * ALTER TABLE = Modificar estructura de tabla
         * IF NOT EXISTS = Solo si no existe ya
         */
        try {
            conn.createStatement().execute(
                "ALTER TABLE curso ADD COLUMN IF NOT EXISTS modalidad VARCHAR(20)");
            System.out.println("✅ Columna 'modalidad' añadida a la tabla");
        } catch(Exception e) {
            System.out.println("ℹ️  Columna 'modalidad' ya existe (normal)");
        }
    }
    
    // ========================================
    // OPCION 4: Buscar por modalidad y precio
    // ========================================
    public void buscarModalidadPrecio(String modalidad, double maxPrecio) throws SQLException {
        /**
         * PreparedStatement con 2 parámetros: modalidad y precio máximo
         */
        PreparedStatement ps = conn.prepareStatement(
            "SELECT * FROM curso WHERE modalidad = ? AND precio < ? ORDER BY precio");
        
        ps.setString(1, modalidad);    // Primer ?
        ps.setDouble(2, maxPrecio);    // Segundo ?
        
        ResultSet rs = ps.executeQuery();
        System.out.println("\n🎯 Cursos " + modalidad + " con precio < " + maxPrecio + "€:");
        
        while(rs.next()) {
            System.out.println("   " + rs.getString("nombre") + 
                             " - " + rs.getDouble("precio") + "€" +
                             " (" + rs.getInt("horas") + "h)");
        }
    }
    
    // ========================================
    // OPCION 5: Total de cursos (función MySQL)
    // ========================================
    public int totalCursos() throws SQLException {
        /**
         * CallableStatement = Llamar función/procedimiento de MySQL
         * { ? = CALL total_cursos() } → ? recibe el resultado
         */
        CallableStatement cs = conn.prepareCall("{? = CALL total_cursos()}");
        cs.registerOutParameter(1, Types.INTEGER);  // ? es un número entero
        cs.execute();  // EJECUTAR función
        return cs.getInt(1);  // OBTENER resultado
    }
}

        /*
         * -- ===== FUNCIÓN MYSQL (phpMyAdmin) =====
DELIMITER //
CREATE FUNCTION total_cursos() 
RETURNS INT
DETERMINISTIC
READS SQL DATA
BEGIN
    DECLARE total INT;
    SELECT COUNT(*) INTO total FROM curso;
    RETURN total;
END//
DELIMITER ;

         * */
        
    
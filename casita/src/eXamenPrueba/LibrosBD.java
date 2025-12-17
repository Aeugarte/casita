package eXamenPrueba;

import java.sql.*;

public class LibrosBD {
    private Connection conn;
    
    public LibrosBD() {
        this.conn = CnxLibroBD.conectar();
        try {
            this.conn.setCatalog("BDLibros");  // ← Automático
        } catch(Exception e) {}
    }
    
    public Connection getConn() { return conn; }
    
    // === MÉTODOS PARA EL MENÚ ===
    public void stockBajo() throws SQLException {
        ResultSet rs = conn.createStatement()
            .executeQuery("SELECT * FROM Libro WHERE stock < 3");
        while(rs.next())
            System.out.println(rs.getInt("ID") + ": " + rs.getString("titulo") 
                + " (stock: " + rs.getInt("stock") + ")");
    }
    
    public void subirPrecioAutor(String autor) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(
            "UPDATE Libro SET precio = precio + 1.5 WHERE autor = ?");
        ps.setString(1, autor);
        System.out.println("✓ " + ps.executeUpdate() + " libros actualizados");
    }
    
    public void addCategoria() throws SQLException {
        try {
            conn.createStatement().execute("ALTER TABLE Libro ADD COLUMN IF NOT EXISTS categoria VARCHAR(200)");
            System.out.println("✓ Categoria añadida");
        } catch(Exception e) {
            System.out.println("✓ Categoria ya existe");
        }
    }

    public void buscarCategoria(String cat, double maxPrecio) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(
            "SELECT * FROM Libro WHERE categoria = ? AND precio < ?");  // ← categoria
        ps.setString(1, cat); 
        ps.setDouble(2, maxPrecio);
        ResultSet rs = ps.executeQuery();
        while(rs.next())
            System.out.println(rs.getString("titulo") + " (" + rs.getDouble("precio") + "€)");
    }

    public int totalLibros() throws SQLException {
        CallableStatement cs = conn.prepareCall("{? = CALL total_libros()}");
        cs.registerOutParameter(1, Types.INTEGER);
        cs.execute();
        return cs.getInt(1);
        /*
         * Necesitas lo siguiente
         * USE bdlibros;

DELIMITER //
CREATE FUNCTION total_libros()
RETURNS INT
DETERMINISTIC
BEGIN
    DECLARE total INT;
    SELECT COUNT(*) INTO total FROM libro;
    RETURN total;
END//
DELIMITER ;
         * 
         * */
    }
}

package ejercicioFinal;

import java.sql.*;

public class ZapatosBD {
    private Connection conn;
    
    public ZapatosBD() {
        this.conn = ConexionBD.conectar();
    }
    
    public void stockBajo() throws SQLException {
        conn.setCatalog("BDZapaton");
        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM zapato WHERE stock < 5");
        while(rs.next())
            System.out.println(rs.getInt("ID") + ": " + rs.getString("marca") + " (stock: " + rs.getInt("stock") + ")");
    }
    
    public void nikeMas2() throws SQLException {
        conn.setCatalog("BDZapaton");
        int f = conn.createStatement().executeUpdate("UPDATE zapato SET precio=precio+2 WHERE marca='Nike'");
        System.out.println(f + " Nike actualizados");
    }
    
    public void addDescripcion() throws SQLException {
        conn.setCatalog("BDZapaton");
        conn.createStatement().execute("ALTER TABLE zapato ADD descripcion VARCHAR(200)");
        System.out.println("✓ Descripción añadida");
    }
    
    // 4. CONSULTA PREPARADA (Rojo y precio < 20)
    public void rojoBarato() throws SQLException {
        conn.setCatalog("BDZapaton");
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM zapato WHERE color=? AND precio<?");
        ps.setString(1, "Rojo");
        ps.setDouble(2, 20);
        ResultSet rs = ps.executeQuery();
        while(rs.next())
            System.out.println(rs.getString("marca") + " " + rs.getString("modelo") + " - " + rs.getDouble("precio") + "€");
    }
    
    // 5. RUTINA TOTAL ZAPATOS
    public void totalZapatos() throws SQLException {
        conn.setCatalog("BDZapaton");
        CallableStatement cs = conn.prepareCall("{? = CALL total_zapatos()}");
        cs.registerOutParameter(1, Types.INTEGER);
        cs.execute();
        System.out.println("Total zapatos: " + cs.getInt(1));
    }
}


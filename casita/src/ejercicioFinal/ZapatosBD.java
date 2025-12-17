package ejercicioFinal;

import java.sql.*;

// Clase que encapsula toda la lógica de acceso a la BD de zapatos
public class ZapatosBD {

    private Connection conn;

    // Al crear el objeto, se abre una conexión a MySQL
    public ZapatosBD() {
        this.conn = ConexionBD.conectar();
    }

    // 1) Mostrar zapatos con stock por debajo de 5 
    public void stockBajo() throws SQLException {
        conn.setCatalog("BDZapaton");
        ResultSet rs = conn.createStatement()
                           .executeQuery("SELECT * FROM zapato WHERE stock < 5");
        while (rs.next()) {
            System.out.println(
                rs.getInt("ID") + ": " +
                rs.getString("marca") +
                " (stock: " + rs.getInt("stock") + ")"
            );
        }
    }

    // 2) Sumar 2 euros al precio de todas las zapatillas Nike
    public void nikeMas2() throws SQLException {
        conn.setCatalog("BDZapaton");
        int f = conn.createStatement()
                    .executeUpdate("UPDATE zapato SET precio = precio + 2 WHERE marca = 'Nike'");
        System.out.println(f + " zapatillas Nike actualizadas (+2€).");
    }

    // 3) Añadir la columna descripcion a la tabla zapato
    public void addDescripcion() throws SQLException {
        conn.setCatalog("BDZapaton");
        conn.createStatement()
            .execute("ALTER TABLE zapato ADD descripcion VARCHAR(200)");
        System.out.println("Columna 'descripcion' añadida a la tabla zapato.");
    }

    // 4) Consulta preparada: zapatos rojos y con precio menor que 20
    public void rojoBarato() throws SQLException {
        conn.setCatalog("BDZapaton");
        PreparedStatement ps = conn.prepareStatement(
            "SELECT * FROM zapato WHERE color = ? AND precio < ?"
        );
        // Fijamos los parámetros de la consulta
        ps.setString(1, "Rojo");
        ps.setDouble(2, 20);

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            System.out.println(
                rs.getString("marca") + " " +
                rs.getString("modelo") + " - " +
                rs.getDouble("precio") + "€"
            );
        }
    }

    // 5) Llamar a la función total_zapatos() del servidor MySQL
    public void totalZapatos() throws SQLException {
        conn.setCatalog("BDZapaton");
        // Llamada a función almacenada que devuelve un entero
        CallableStatement cs = conn.prepareCall("{? = CALL total_zapatos()}");
        cs.registerOutParameter(1, Types.INTEGER);  // Registramos el parámetro de salida
        cs.execute();                               // Ejecutamos la función
        System.out.println("Total de zapatos en la tabla: " + cs.getInt(1));
    }

    /*
     * Función necesaria en MySQL (creada en phpMyAdmin):
     *
     * USE BDZapaton;
     *
     * DELIMITER //
     * DROP FUNCTION IF EXISTS total_zapatos//
     * CREATE FUNCTION total_zapatos()
     * RETURNS INT
     * DETERMINISTIC
     * READS SQL DATA
     * BEGIN
     *   DECLARE total INT;
     *   SELECT COUNT(*) INTO total FROM zapato;
     *   RETURN total;
     * END//
     * DELIMITER ;
     */
}

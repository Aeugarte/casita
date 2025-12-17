package ejercicioFinal;


public class Zapato {

    // Atributos que coinciden con las claves del JSON y las columnas de la BD
    private String marca;
    private String modelo;
    private String tamano;
    private String color;
    private int stock;
    private double precio;

    // Constructor vacío obligatorio para que Jackson pueda instanciar la clase
    public Zapato() {}

    // Getters y setters estándar para que Jackson y JDBC puedan acceder a los datos
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public String getTamano() { return tamano; }
    public void setTamano(String tamano) { this.tamano = tamano; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
}

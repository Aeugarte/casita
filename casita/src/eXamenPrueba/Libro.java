package eXamenPrueba;

public class Libro {
	// Zapato.java (POJO individual)

	    private String titulo, autor;
	    private int stock;
	    private double precio;
	    
	    // Constructor vacío (obligatorio para Jackson)
	    public Libro() {}

		public String getTitulo() {
			return titulo;
		}

		public void setTitulo(String titulo) {
			this.titulo = titulo;
		}

		public String getAutor() {
			return autor;
		}

		public void setAutor(String autor) {
			this.autor = autor;
		}

		public int getStock() {
			return stock;
		}

		public void setStock(int stock) {
			this.stock = stock;
		}

		public double getPrecio() {
			return precio;
		}

		public void setPrecio(double precio) {
			this.precio = precio;
		}
	    
	    // Getters y Setters
	  
	

}

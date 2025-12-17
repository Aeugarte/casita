package eXamenPrueba;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LibrosJson {
	@JsonProperty("libros")  // ← Quita @JsonProperty o ponlo en array raíz
	private List<Libro> libros;  // ← Especifica Libro, no List genérico

	    
	    public List<Libro> getLibros() { return libros; }
	    public void setlibros(List<Libro> libros) { this.libros = libros;}

}

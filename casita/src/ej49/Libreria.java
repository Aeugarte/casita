package ej49;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

public class Libreria {
    private List<Libro> libros;
    public static final String ARCHIVO = "datosPaqui.json";
    private ObjectMapper objectMapper;

    public Libreria() {
        this.libros = new ArrayList<>();
        this.objectMapper = new ObjectMapper();
        // Formatear el JSON para que sea legible
        this.objectMapper.writerWithDefaultPrettyPrinter();
        cargarDatos();
    }

    public Libreria(List<Libro> libros) {
        this.libros = libros;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.writerWithDefaultPrettyPrinter();
        cargarDatos();
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

    // Cargar datos desde JSON
    private void cargarDatos() {
        File archivo = new File(ARCHIVO);
        if (archivo.exists()) {
            try {
                // Definir el tipo de colección para Jackson
                CollectionType listType = objectMapper.getTypeFactory()
                    .constructCollectionType(List.class, Libro.class);
                
                this.libros = objectMapper.readValue(archivo, listType);
                System.out.println("Datos cargados desde " + ARCHIVO);
            } catch (Exception e) {
                System.out.println("Error al cargar los datos: " + e.getMessage());
                // Si hay error al cargar, inicializamos con lista vacía
                this.libros = new ArrayList<>();
            }
        } else {
            System.out.println("No se encontró el archivo " + ARCHIVO + ". Se creará uno nuevo al guardar.");
        }
    }

    // Guardar datos en JSON
    public void guardar() {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(ARCHIVO), libros);
            System.out.println("Datos guardados en " + ARCHIVO);
        } catch (Exception e) {
            System.out.println("Error al guardar los datos: " + e.getMessage());
        }
    }

    // Agregar nuevo libro
    public boolean agregarLibro(Libro libro) {
        // Verificar si ya existe un libro con el mismo ISBN
        for (Libro l : libros) {
            if (l.getIsbn().equals(libro.getIsbn())) {
                return false; // ISBN duplicado
            }
        }
        libros.add(libro);
        return true;
    }

    // Modificar libro por ISBN
    public boolean modificarLibro(String isbn, String nuevoTitulo, String nuevoAutor, String nuevaEditorial) {
        for (Libro libro : libros) {
            if (libro.getIsbn().equals(isbn)) {
                libro.setTitulo(nuevoTitulo);
                libro.setAutor(nuevoAutor);
                libro.setEditorial(nuevaEditorial);
                return true;
            }
        }
        return false; // No se encontró el libro
    }

    // Borrar libro por ISBN
    public boolean borrarLibro(String isbn) {
        for (int i = 0; i < libros.size(); i++) {
            if (libros.get(i).getIsbn().equals(isbn)) {
                libros.remove(i);
                return true;
            }
        }
        return false; // No se encontró el libro
    }

    // Buscar libro por ISBN
    public Libro buscarLibroPorIsbn(String isbn) {
        for (Libro libro : libros) {
            if (libro.getIsbn().equals(isbn)) {
                return libro;
            }
        }
        return null;
    }

    // Mostrar todos los libros
    public void mostrarLibros() {
        if (libros.isEmpty()) {
            System.out.println("No hay libros en la librería.");
        } else {
            System.out.println("\n--- Libros en la librería ---");
            for (int i = 0; i < libros.size(); i++) {
                System.out.println((i + 1) + ". " + libros.get(i));
            }
        }
    }
}
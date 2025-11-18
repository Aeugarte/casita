package ej49;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner leer = new Scanner(System.in);
        Libreria libreria = new Libreria();
        int opcion;

        do {
            System.out.println("\n--- Librería Paqui ---");
            System.out.println("1. Nuevo libro");
            System.out.println("2. Modificar libro por ISBN");
            System.out.println("3. Borrar libro por ISBN");
            System.out.println("4. Salir y guardar");
            System.out.print("Opción: ");

            opcion = leer.nextInt();
            leer.nextLine(); // Limpiar el buffer

            switch (opcion) {
                case 1:
                    nuevoLibro(leer, libreria);
                    break;
                case 2:
                    modificarLibro(leer, libreria);
                    break;
                case 3:
                    borrarLibro(leer, libreria);
                    break;
                case 4:
                    libreria.guardar();
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo");
                    break;
            }
        } while (opcion != 4);
        
        leer.close();
    }

    private static void nuevoLibro(Scanner leer, Libreria libreria) {
        System.out.println("\n--- Nuevo Libro ---");
        System.out.print("Título: ");
        String titulo = leer.nextLine();
        System.out.print("Autor: ");
        String autor = leer.nextLine();
        System.out.print("Editorial: ");
        String editorial = leer.nextLine();
        System.out.print("ISBN: ");
        String isbn = leer.nextLine();

        if (titulo.isEmpty() || autor.isEmpty() || editorial.isEmpty() || isbn.isEmpty()) {
            System.out.println("Error: Todos los campos son obligatorios.");
            return;
        }

        Libro nuevoLibro = new Libro(titulo, autor, editorial, isbn);
        if (libreria.agregarLibro(nuevoLibro)) {
            System.out.println("Libro agregado correctamente.");
        } else {
            System.out.println("Error: Ya existe un libro con ese ISBN.");
        }
    }

    private static void modificarLibro(Scanner leer, Libreria libreria) {
        System.out.println("\n--- Modificar Libro ---");
        System.out.print("ISBN del libro a modificar: ");
        String isbn = leer.nextLine();

        Libro libro = libreria.buscarLibroPorIsbn(isbn);
        if (libro == null) {
            System.out.println("Error: No se encontró un libro con ese ISBN.");
            return;
        }

        System.out.println("Libro encontrado: " + libro);
        System.out.print("Nuevo título (actual: " + libro.getTitulo() + "): ");
        String nuevoTitulo = leer.nextLine();
        System.out.print("Nuevo autor (actual: " + libro.getAutor() + "): ");
        String nuevoAutor = leer.nextLine();
        System.out.print("Nueva editorial (actual: " + libro.getEditorial() + "): ");
        String nuevaEditorial = leer.nextLine();

        if (libreria.modificarLibro(isbn, 
            nuevoTitulo.isEmpty() ? libro.getTitulo() : nuevoTitulo,
            nuevoAutor.isEmpty() ? libro.getAutor() : nuevoAutor,
            nuevaEditorial.isEmpty() ? libro.getEditorial() : nuevaEditorial)) {
            System.out.println("Libro modificado correctamente.");
        } else {
            System.out.println("Error al modificar el libro.");
        }
    }

    private static void borrarLibro(Scanner leer, Libreria libreria) {
        System.out.println("\n--- Borrar Libro ---");
        System.out.print("ISBN del libro a borrar: ");
        String isbn = leer.nextLine();

        Libro libro = libreria.buscarLibroPorIsbn(isbn);
        if (libro == null) {
            System.out.println("Error: No se encontró un libro con ese ISBN.");
            return;
        }

        System.out.println("Libro a borrar: " + libro);
        System.out.print("¿Está seguro de que desea borrar este libro? (s/n): ");
        String confirmacion = leer.nextLine();

        if (confirmacion.equalsIgnoreCase("s")) {
            if (libreria.borrarLibro(isbn)) {
                System.out.println("Libro borrado correctamente.");
            } else {
                System.out.println("Error al borrar el libro.");
            }
        } else {
            System.out.println("Operación cancelada.");
        }
    }
}
package ejercicioFinal;

import java.util.Scanner;

// Clase con el menú por consola que llama a los métodos de ZapatosBD
public class Ej2MenuPrincipal {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // Creamos el objeto de lógica de negocio para la BD de zapatos
        ZapatosBD zbd = new ZapatosBD();

        try {
            int opcion;
            do {
                // Mostrar menú de opciones al usuario
                System.out.println("\n=== MENÚ ZAPATOS ===");
                System.out.println("1. Mostrar zapatos con stock < 5");
                System.out.println("2. Subir 2€ al precio de las Nike");
                System.out.println("3. Añadir columna descripcion");
                System.out.println("4. Ver zapatos rojos con precio < 20");
                System.out.println("5. Mostrar total de zapatos (función SQL)");
                System.out.println("0. Salir");
                System.out.print("Opción: ");
                opcion = sc.nextInt();

                // Según la opción, llamamos al método correspondiente
                switch (opcion) {
                    case 1:
                        zbd.stockBajo();
                        break;
                    case 2:
                        zbd.nikeMas2();
                        break;
                    case 3:
                        zbd.addDescripcion();
                        break;
                    case 4:
                        zbd.rojoBarato();
                        break;
                    case 5:
                        zbd.totalZapatos();
                        break;
                    case 0:
                        System.out.println("Saliendo del programa...");
                        break;
                    default:
                        System.out.println("Opción no válida.");
                }
            } while (opcion != 0);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sc.close();  // Cerramos el scanner al final
        }
    }
}

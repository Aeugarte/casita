// ===== EJ2MENUPRINCIPAL.JAVA =====
package examenXML;

import java.util.Scanner;

public class Ej2MenuPrincipal {
    public static void main(String[] args) {
        CursoBD bd = new CursoBD();
        Scanner sc = new Scanner(System.in);
        
        try {
            int opcion;
            do {
                System.out.println("\n=== MENÚ CURSOS ===");
                System.out.println("1. Cursos < 35h");
                System.out.println("2. +20€ profesor");
                System.out.println("3. Añadir modalidad");
                System.out.println("4. Modalidad + precio<200");
                System.out.println("5. Total cursos");
                System.out.println("0. Salir");
                System.out.print("Opción: ");
                opcion = sc.nextInt();
                sc.nextLine(); // Limpiar buffer
                
                switch(opcion) {
                    case 1: bd.cursosCortos(); break;
                    case 2: 
                        System.out.print("Profesor: ");
                        bd.subirPrecioProfesor(sc.nextLine()); break;
                    case 3: bd.addModalidad(); break;
                    case 4: 
                        System.out.print("Modalidad: ");
                        bd.buscarModalidadPrecio(sc.nextLine(), 200); break;
                    case 5: 
                        System.out.println("Total cursos: " + bd.totalCursos()); break;
                }
            } while(opcion != 0);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}

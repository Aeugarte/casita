package eXamenPrueba;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.Scanner;

import eXamenPrueba.LibrosBD;

public class Ej2MenuPrincipal {
	   public static void main(String[] args) {
	        Connection conn = CnxLibroBD.conectar();
	        Scanner sc = new Scanner(System.in);
	        // En Ej2MenuPrincipal.java
            LibrosBD bd = new LibrosBD();  // 
	        try {
	            conn.setCatalog("BDZapaton");
	            
	            int opcion;
	            do {
	                System.out.println("\n=== MENÚ ZAPATOS ===");
	                System.out.println("1. Stock < 3");
	                System.out.println("2. +1,5€ autor");
	                System.out.println("3. Añadir cateogoria");
	                System.out.println("4. categoria y precio<10");
	                System.out.println("5. Total libros");
	                System.out.println("0. Salir");
	                System.out.print("Opción: ");
	                opcion = sc.nextInt();
	                
	                switch(opcion) {
	          

	                case 1: bd.stockBajo(); break;
	                case 2: 
	                    System.out.print("Autor: "); sc.nextLine();
	                    bd.subirPrecioAutor(sc.nextLine()); break;
	                case 3:
	                	bd.addCategoria();break;
	                case 4: 
	                    System.out.print("Categoría: "); sc.nextLine();
	                    bd.buscarCategoria(sc.nextLine(), 20); break;
	                case 5: System.out.println("Total: " + bd.totalLibros()); break;

	                }
	            } while(opcion != 0);
	            
	        } catch(Exception e) {
	            e.printStackTrace();
	        }
	    }
}

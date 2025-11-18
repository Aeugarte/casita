package PruebaExamen;

import java.io.RandomAccessFile;
import java.util.Scanner;

public class Ej6 {
    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);
        
        try {
            RandomAccessFile archivoBinario = new RandomAccessFile("telefonos.bin", "r");
            
            long totalBytes = archivoBinario.length();
            int tamanoRegistro = 22;
            int totalRegistros = (int) (totalBytes / tamanoRegistro);
            
            System.out.println("=== BUSCADOR DE TELÉFONOS ===");
            System.out.println("Registros en archivo: " + totalRegistros);
            
            while (true) {
                System.out.print("\nIntroduce DNI a buscar (o 'salir' para terminar): ");
                String dniBuscado = scanner.nextLine();
                
                if (dniBuscado.equalsIgnoreCase("salir")) {
                    break;
                }
                
                // Formatear DNI
                String dniFormateado = String.format("%-9s", dniBuscado).substring(0, 9);
                boolean encontrado = false;
                
                // Buscar en todos los registros
                for (int i = 0; i < totalRegistros; i++) {
                    long posicion = i * tamanoRegistro;
                    archivoBinario.seek(posicion);
                    
                    // Leer DNI
                    char[] dniChars = new char[9];
                    for (int j = 0; j < 9; j++) {
                        dniChars[j] = archivoBinario.readChar();
                    }
                    String dniArchivo = new String(dniChars);
                    
                    if (dniArchivo.equals(dniFormateado)) {
                        int telefono = archivoBinario.readInt();
                        System.out.println("✅ ENCONTRADO - DNI: " + dniArchivo.trim() + " - Teléfono: " + telefono);
                        encontrado = true;
                        break;
                    }
                }
                
                if (!encontrado) {
                    System.out.println("❌ No encontrado: " + dniBuscado);
                }
            }
            
            System.out.println("¡Hasta pronto!");
            archivoBinario.close();
            scanner.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
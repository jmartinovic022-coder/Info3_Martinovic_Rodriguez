import java.util.*;

public class Ejercicio_1 {
   public static void main(String[] args) {
        ArbolAVL arbol = new ArbolAVL();
        int[] secuencia = {40, 20, 10, 25, 30, 22, 50};

        System.out.println("Iniciando Inserción de la secuencia: 30, 20, 10, 40, 50, 60");
        System.out.println("-----------------------------------------------------------");

    int paso = 1;
        for (int valor : secuencia) {
            System.out.println("\n--- Paso " + paso + ": Insertando " + valor + " ---");
            arbol.insertar(valor);
            arbol.dibujar();
            System.out.print("Inorden: ");
            arbol.inorden();
            System.out.println("-----------------------------------------------------------");
            paso++;
        }

        // Prueba de robustez
        System.out.println("\n--- Prueba de Robustez (entrada no numérica) ---");
        // Usar try-with-resources para asegurar cierre del Scanner incluso si ocurre una excepción
        try (Scanner scanner = new Scanner(System.in)) {
            boolean entradaValida = false;
            while (!entradaValida) {
                System.out.print("Intenta ingresar un número: ");
                try {
                    int nuevoValor = scanner.nextInt();
                    arbol.insertar(nuevoValor);
                    System.out.println("Número " + nuevoValor + " insertado correctamente.");
                    arbol.dibujar();
                    entradaValida = true;
                } catch (InputMismatchException e) {
                    System.out.println("⚠️ ERROR: Entrada inválida. Solo se permiten números.");
                    scanner.next(); // limpiar buffer
                }
            }
        }
    }
}
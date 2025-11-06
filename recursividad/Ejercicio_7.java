/* Escriba una función recursiva para calcular el n-ésimo número de Fibonacci. */
package recursividad;
import java.util.Scanner;

public class Ejercicio_7 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Ingrese un número entero positivo para calcular su Fibonacci: ");
        int n = sc.nextInt();
        sc.close();
        if (n < 0) {
            System.out.println("Por favor, ingrese un número entero positivo.");
        } else {
            int resultado = fibonacci(n);
            System.out.println("El " + n + "º número de Fibonacci es: " + resultado);
        }
    }

    public static int fibonacci(int n) {
        if (n == 0) {
            return 0; // Caso base: Fibonacci(0) = 0
        } else if (n == 1) {
            return 1; // Caso base: Fibonacci(1) = 1
        } else {
            return fibonacci(n - 1) + fibonacci(n - 2); // Llamada recursiva
        }
    }
}

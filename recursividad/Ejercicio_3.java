package recursividad;
import java.util.Scanner;
/* Implemente una función recursiva que calcule la suma de todos los elementos de un arreglo
de enteros.
Ejemplo: [2, 4, 6, 8] → 20.

👉 Extienda la solución para que además devuelva el promedio usando únicamente
recursión. 
 */
public class Ejercicio_3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Ingrese el tamaño del arreglo: ");
        int n = sc.nextInt();
        int[] arreglo = new int[n];
        
        for (int i = 0; i < n; i++) {
            System.out.print("Ingrese el elemento " + (i + 1) + ": ");
            arreglo[i] = sc.nextInt();
        }
        
        int suma = suma(arreglo, n);
        double promedio = promedio(suma, n);
        
        System.out.println("La suma de los elementos es: " + suma);
        System.out.println("El promedio de los elementos es: " + promedio);
        
        sc.close();
    }

    public static int suma(int[] arr, int n) {
        if (n <= 0) {
            return 0;
        } else {
            return arr[n - 1] + suma(arr, n - 1);
        }
    }
    private static double promedio(int suma, int n) {
        if (n == 1) {
            return suma; // caso base
        } else {
            // Hacemos la división recursiva
            return (promedio(suma, n - 1) * (n - 1) + (double) suma / n) / n * n; 
        }
    }
}

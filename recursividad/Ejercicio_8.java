/* Implemente un método recursivo que determine si un número se encuentra dentro de un
arreglo de enteros.
Ejemplo: [3, 5, 7, 9], buscar 7 → true. */

package recursividad;
import java.util.Scanner;

public class Ejercicio_8 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[] arreglo = {3, 5, 7, 9};
        System.out.print("Ingrese un número entero para buscar en el arreglo: ");
        int numeroABuscar = sc.nextInt();
        sc.close();
        boolean encontrado = buscarEnArreglo(arreglo, numeroABuscar, 0);
        if (encontrado) {
            System.out.println("El número " + numeroABuscar + " se encuentra en el arreglo.");
        } else {
            System.out.println("El número " + numeroABuscar + " no se encuentra en el arreglo.");
        }
    }

    public static boolean buscarEnArreglo(int[] arreglo, int numero, int indice) {
        if (indice >= arreglo.length) {
            return false; // Caso base: se ha recorrido todo el arreglo sin encontrar el número
        }
        if (arreglo[indice] == numero) {
            return true; // El número fue encontrado
        }
        return buscarEnArreglo(arreglo, numero, indice + 1); // Llamada recursiva
    }
}

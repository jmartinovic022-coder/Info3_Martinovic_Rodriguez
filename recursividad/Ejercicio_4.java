/*Implemente de manera recursiva el algoritmo de Euclides para calcular el MCD de dos
números enteros positivos.
Ejemplo: MCD(48, 18) = 6.*/
package recursividad;
import java.util.Scanner;

public class Ejercicio_4 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Ingrese el primer número entero positivo: ");
        int a = sc.nextInt();
        System.out.print("Ingrese el segundo número entero positivo: ");
        int b = sc.nextInt();
        int mcd = mcd(a, b);
        System.out.println("El MCD de " + a + " y " + b + " es: " + mcd);
        sc.close();
    }

    public static int mcd(int a, int b) {
        if (b == 0) {
            return a;
        } else {
            return mcd(b, a % b);
        }
    }
}

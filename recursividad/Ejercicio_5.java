/*Escriba un método recursivo que reciba un número entero positivo y devuelva su
representación en binario (como string).
Ejemplo: 13 → "1101". */

package recursividad;
import java.util.Scanner;

public class Ejercicio_5 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Ingrese un número entero positivo: ");
        int numero = sc.nextInt();
        sc.close();
        String binario = convertirABinario(numero);
        System.out.println("El número " + numero + " en binario es: " + binario);
    }

    public static String convertirABinario(int numero) {
        if (numero == 0) {
            return "0";
        } else if (numero == 1) {
            return "1";
        } else {
            return convertirABinario(numero / 2) + (numero % 2);
        }
    }
}

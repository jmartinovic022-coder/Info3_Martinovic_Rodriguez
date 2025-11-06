/*Cree una función recursiva que determine si una cadena es un palíndromo.
Ejemplo: "neuquen" → true, "informatica" → false. */

package recursividad;
import java.util.Scanner;

public class Ejercicio_6 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Ingrese una cadena: ");
        String cadena = sc.nextLine();
        sc.close();
        boolean esPalindromo = esPalindromo(cadena);
        if (esPalindromo) {
            System.out.println("La cadena \"" + cadena + "\" es un palíndromo.");
        } else {
            System.out.println("La cadena \"" + cadena + "\" no es un palíndromo.");
        }
    }
    public static boolean esPalindromo(String cadena) {
        cadena = cadena.replaceAll("\\s+", "").toLowerCase(); // Eliminar espacios y convertir a minúsculas
        return esPalindromoRecursivo(cadena, 0, cadena.length() - 1);
    }

    private static boolean esPalindromoRecursivo(String cadena, int inicio, int fin) {
        if (inicio >= fin) {
            return true; // Caso base: se han comparado todos los caracteres
        }
        if (cadena.charAt(inicio) != cadena.charAt(fin)) {
            return false; // Los caracteres no coinciden
        }
        return esPalindromoRecursivo(cadena, inicio + 1, fin - 1); // Llamada recursiva
    }
}

package recursividad;
import java.util.Scanner;

/* Escriba un método recursivo que reciba una cadena y devuelva la misma cadena invertida.
Ejemplo: "recursivo" → "ovisrucer". */

public class Ejercicio_2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Ingrese una cadena: ");
        String cadena = sc.nextLine();
        String resultado = invertir(cadena);
        System.out.println("La cadena invertida es: " + resultado);
        sc.close();
    }
    public static String invertir(String str) {
        if (str.isEmpty()) {
            return str;
        } else {
            return str.charAt(str.length() - 1) + invertir(str.substring(0, str.length() - 1));
        }
    }
}

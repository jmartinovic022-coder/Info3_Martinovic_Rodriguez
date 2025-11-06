package recursividad;
import java.util.Scanner;
/* 
Escriba una función recursiva que calcule cuántos dígitos tiene un número entero positivo.
Ejemplo: 12345 → 5. 
*/

public class Ejercicio_1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int numero; 
        System.out.print("Ingrese un número entero positivo: ");
        numero = sc.nextInt();
        int resultado = conteo(numero);
        System.out.println("El número " + numero + " tiene " + resultado + " dígitos.");
        sc.close();
    }
    public static int conteo(int x){
        if (x < 10) {
            return 1;
        } else {
            return 1 + conteo(x / 10);
        }
    }
}

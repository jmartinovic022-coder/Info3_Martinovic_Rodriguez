import java.util.*;
import pila_cola.PilaArreglo;
import pila_cola.ColaArreglo;


public class App {
    public static void main(String[] args) throws Exception {
        String palabra;
        Scanner sc = new Scanner(System.in);
        System.out.print("Ingrese una palabra para checkear si es palindromo: ");
        palabra = sc.nextLine();
        PilaArreglo<Character> pila = new PilaArreglo<>(palabra.length());
        ColaArreglo<Character> cola = new ColaArreglo<>(palabra.length());
        for(int i = 0; i < palabra.length(); i++) {
            pila.push(palabra.charAt(i));
            cola.enqueue(palabra.charAt(i));
        }
        for(int i = 0; i < palabra.length(); i++) {
            if(pila.pop() != cola.dequeue()) {
                System.out.println("La palabra " + palabra + " no es un palíndromo.");
                sc.close();
                return;
            }
        }
        System.out.println("La palabra " + palabra + " es un palíndromo.");
        sc.close();
    }
}

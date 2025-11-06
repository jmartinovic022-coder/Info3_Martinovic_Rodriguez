package pila_cola.Ejercicio_1;

import pila_cola.PilaArreglo;

public class Test {
    public static void main(String[] args) {
        PilaArreglo<Integer> pila = new PilaArreglo<>(5);

        System.out.println("¿Está vacía? " + pila.isEmpty());
        
        pila.push(10);
        pila.push(20);
        pila.push(30);
        pila.push(40);
        pila.push(50);

        pila.mostrar();
        System.out.println("¿Está llena? " + pila.isFull());
        pila.push(60);

        pila.mostrar();

        System.out.println("Elemento en top(): " + pila.top());

        System.out.println("Se sacó: " + pila.pop());
        pila.mostrar();
        System.out.println("Se sacó: " + pila.pop());
        pila.mostrar();

        System.out.println("¿Está vacía? " + pila.isEmpty());
        System.out.println("¿Está llena? " + pila.isFull());
    }
}

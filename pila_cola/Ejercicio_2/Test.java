package pila_cola.Ejercicio_2;

import pila_cola.ColaArreglo;

public class Test {
        public static void main(String[] args) {
        ColaArreglo<Integer> cola = new ColaArreglo<>(5);

        cola.enqueue(1);
        cola.enqueue(2);
        cola.enqueue(3);
        cola.enqueue(4);
        cola.mostrar();

        System.out.println("Elemento en top(): " + cola.top());

        System.out.println("Se desencoló: " + cola.dequeue());
        cola.mostrar();

        System.out.println("¿Está vacía? " + cola.isEmpty());
        System.out.println("¿Está llena? " + cola.isFull());
    }
}

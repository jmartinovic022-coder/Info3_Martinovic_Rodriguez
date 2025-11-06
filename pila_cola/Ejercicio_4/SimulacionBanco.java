package pila_cola.Ejercicio_4;
import pila_cola.ColaArreglo;

public class SimulacionBanco {
    public static void main(String[] args) {
        ColaArreglo<String> cola = new ColaArreglo<>(10);

        cola.enqueue("Ana");
        cola.enqueue("Luis");
        cola.enqueue("Marta");
        cola.enqueue("Pedro");

        System.out.println("Cola inicial:");
        cola.mostrar();

        System.out.println("Atendido: " + cola.dequeue());
        System.out.println("Atendido: " + cola.dequeue());

        System.out.println("Cola después de atender:");
        cola.mostrar();
    }
}


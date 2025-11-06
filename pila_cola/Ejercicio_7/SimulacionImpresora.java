package pila_cola.Ejercicio_7;
import pila_cola.ColaArreglo;

public class SimulacionImpresora {
    public static void main(String[] args) {
        ColaArreglo<String> colaImpresion = new ColaArreglo<>(10);

        // Llegan 5 documentos
        colaImpresion.enqueue("doc1.txt");
        colaImpresion.enqueue("doc2.txt");
        colaImpresion.enqueue("doc3.txt");
        colaImpresion.enqueue("doc4.txt");
        colaImpresion.enqueue("doc5.txt");

        System.out.println("Cola de impresión inicial:");
        colaImpresion.mostrar();

        // Se imprimen 3 documentos
        System.out.println("\n--- Procesando impresión ---");
        for (int i = 0; i < 3; i++) {
            String doc = colaImpresion.dequeue();
            if (doc != null) {
                System.out.println("Imprimiendo " + doc+"...");
            }
        }

        // Estado final de la cola
        System.out.println("\nCola de impresión después de imprimir 3 documentos:");
        colaImpresion.mostrar();
    }
}

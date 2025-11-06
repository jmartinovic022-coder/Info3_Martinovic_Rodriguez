package monticulobinario;

import java.util.Arrays;

/**
 * MinHeapDemo.java
 * Archivo separado que contiene el main de pruebas para MinHeap y la
 * implementación auxiliar de MaxHeap (no pública).
 */
public class MinHeapDemo {
    public static void main(String[] args) {
        System.out.println("=== Pruebas MinHeap: ejercicios 1-6 y 9 ===");
        MinHeap heap = new MinHeap();

        // Ejercicio 1 y 2: Agregar 20, 5, 15, 3, 11 y mostrarlos en orden de extracción.
        int[] toAdd = {20, 5, 15, 3, 11};
        System.out.println("\nEjercicio 1 & 2: Insertar y mostrar intercambios:");
        for (int v : toAdd) heap.add(v);

        System.out.println("\nExtrayendo todos los elementos:");
        while (!heap.isEmpty()) {
            System.out.println("poll() -> " + heap.poll());
        }

        // Ejercicio 5: heapify - construir desde arreglo
        System.out.println("\nEjercicio 5: Construcción desde arreglo (heapify):");
        int[] datos = {9, 4, 7, 1, 6, 2};
        MinHeap heapFromArray = new MinHeap(Arrays.copyOf(datos, datos.length));
        // Usar la instancia para evitar la variable no leída y mostrar su estado
        heapFromArray.printArray();
        heapFromArray.printTree();

        // Ejercicio 6: heapsort
        int[] arr = {9, 4, 7, 1, 6, 2};
        MinHeap.heapsort(arr);

        // Ejercicio 7: demo MaxHeap (implementada abajo en este archivo)
        System.out.println("\n--- Demo MaxHeap (Ejercicio 7) ---");
        int[] datosMax = {10, 3, 15, 8, 6, 12};
        MaxHeap max = new MaxHeap();
        System.out.println("Insertando: " + Arrays.toString(datosMax));
        for (int v : datosMax) max.add(v);
        System.out.println("Orden de eliminación (mayor a menor):");
        while (!max.isEmpty()) {
            System.out.print(max.poll() + " ");
        }
        System.out.println();

        // Ejercicio 9: seguimiento del estado interno ya se muestra en cada add/poll
        System.out.println("\nFin de pruebas MinHeap.");
    }
}

/*
 * Implementación simple de MaxHeap (Ejercicio 7).
 * Clase no pública dentro del mismo archivo para mantener todo en MinHeapDemo.java
 */
class MaxHeap {
    private int[] heap;
    private int size;

    public MaxHeap() {
        heap = new int[10];
        size = 0;
    }

    public void add(int valor) {
        if (size >= heap.length) heap = Arrays.copyOf(heap, heap.length * 2);
        heap[size] = valor;
        percolateUp(size);
        size++;
    }

    private void percolateUp(int index) {
        int current = index;
        while (current > 0) {
            int p = (current - 1) / 2;
            if (heap[current] > heap[p]) {
                swap(current, p);
                current = p;
            } else break;
        }
    }

    public int poll() {
        if (size == 0) throw new IllegalStateException("MaxHeap vacío");
        int r = heap[0];
        heap[0] = heap[size - 1];
        heap[size - 1] = 0;
        size--;
        percolateDown(0);
        return r;
    }

    private void percolateDown(int index) {
        int current = index;
        while (true) {
            int left = 2 * current + 1;
            int right = 2 * current + 2;
            int largest = current;
            if (left < size && heap[left] > heap[largest]) largest = left;
            if (right < size && heap[right] > heap[largest]) largest = right;
            if (largest != current) {
                swap(current, largest);
                current = largest;
            } else break;
        }
    }

    public boolean isEmpty() { return size == 0; }

    private void swap(int i, int j) {
        int t = heap[i]; heap[i] = heap[j]; heap[j] = t;
    }
}

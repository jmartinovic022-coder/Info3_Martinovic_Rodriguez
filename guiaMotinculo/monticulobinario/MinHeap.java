
package monticulobinario;

import java.util.Arrays;

/*
 * MinHeap.java
 * Contiene implementaciones y pruebas para los ejercicios:
 * 1, 2, 3, 4, 5, 6 y 9 del práctico.
 *
 * Ejercicio 1: Estructura básica y métodos add, poll, peek, isEmpty.
 * Ejercicio 2: percolateUp (mostrando intercambios).
 * Ejercicio 3: percolateDown (mostrando arreglo antes/después de poll).
 * Ejercicio 4: printTree() (muestra el heap por niveles).
 * Ejercicio 5: constructor heapify(int[] datos) (muestra pasos).
 * Ejercicio 6: heapsort(int[] arr) (método estático).
 * Ejercicio 9: printArray() (muestra estado interno tras cada operación).
 *
 * Java 17 - Código en español y con salidas por consola.
 */

public class MinHeap {
    private int[] heap;
    private int size;

    // Ejercicio 1: estructura básica
    public MinHeap() {
        heap = new int[10];
        size = 0;
    }

    // Ejercicio 5: constructor heapify (bottom-up)
    // Muestra paso a paso cómo se reorganiza el arreglo original.
    public MinHeap(int[] datos) {
        // copiamos datos (no modificar el arreglo original)
        heap = Arrays.copyOf(datos, datos.length);
        size = datos.length;
        System.out.println("Heapify - arreglo inicial: " + Arrays.toString(heap));
        // desde el último padre hacia la raíz
        for (int i = parent(size - 1); i >= 0; i--) {
            System.out.println("Aplicando percolateDown desde índice " + i + " (valor " + heap[i] + ")");
            percolateDownHeapify(i);
            System.out.println("Estado intermedio: " + Arrays.toString(heap));
        }
        System.out.println("Heapify - arreglo final: " + Arrays.toString(heap));
    }

    // Ejercicio 1: add (llama a percolateUp; Ejercicio 2 pide mostrar intercambios)
    public void add(int valor) {
        ensureCapacity();
        heap[size] = valor;
        System.out.println("\nInsertando " + valor + " en posición " + size);
        percolateUp(size);
        size++;
        printArray(); // Ejercicio 9: seguimiento del estado interno
        printTree();  // Ejercicio 4: mostrar estructura
    }

    // Ejercicio 2: percolateUp con impresión de intercambios
    private void percolateUp(int index) {
        int current = index;
        while (current > 0) {
            int p = parent(current);
            if (heap[current] < heap[p]) {
                System.out.println("Intercambio (percolateUp): índice " + current + " ("
                        + heap[current] + ") <-> índice " + p + " (" + heap[p] + ")");
                swap(current, p);
                current = p;
            } else {
                break;
            }
        }
    }

    // Ejercicio 3: poll (eliminar mínimo) con percolateDown y mostrando arreglo antes/después
    public int poll() {
        if (isEmpty()) throw new IllegalStateException("El heap está vacío");
        System.out.println("\n--- Eliminando raíz (mínimo) ---");
        System.out.println("Arreglo antes de poll: " + Arrays.toString(getInternalArraySnapshot()));
        int result = heap[0];
        heap[0] = heap[size - 1];
        heap[size - 1] = 0;
        size--;
        if (size > 0) percolateDown(0);
        System.out.println("Arreglo después de poll: " + Arrays.toString(getInternalArraySnapshot()));
        printArray(); // Ejercicio 9
        printTree();  // Ejercicio 4
        return result;
    }

    // percolateDown usado en poll (Ejercicio 3)
    private void percolateDown(int index) {
        int current = index;
        while (true) {
            int left = leftChild(current);
            int right = rightChild(current);
            int smallest = current;
            if (left < size && heap[left] < heap[smallest]) smallest = left;
            if (right < size && heap[right] < heap[smallest]) smallest = right;
            if (smallest != current) {
                System.out.println("Intercambio (percolateDown): índice " + current + " (" + heap[current] + ") <-> índice " + smallest + " (" + heap[smallest] + ")");
                swap(current, smallest);
                current = smallest;
            } else {
                break;
            }
        }
    }

    // percolateDown usado por heapify (sin prints de poll antes/después)
    private void percolateDownHeapify(int index) {
        int current = index;
        while (true) {
            int left = leftChild(current);
            int right = rightChild(current);
            int smallest = current;
            if (left < size && heap[left] < heap[smallest]) smallest = left;
            if (right < size && heap[right] < heap[smallest]) smallest = right;
            if (smallest != current) {
                swap(current, smallest);
                current = smallest;
            } else break;
        }
    }

    // Ejercicio 1: peek e isEmpty
    public int peek() {
        if (isEmpty()) throw new IllegalStateException("El heap está vacío");
        return heap[0];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    // Ejercicio 9: printArray - muestra el arreglo interno actual (solo la porción usada)
    public void printArray() {
        System.out.println("Estado interno (tamaño=" + size + "): " + Arrays.toString(getInternalArraySnapshot()));
    }

    // Ejercicio 4: printTree - muestra el heap por niveles (una línea por nivel)
    public void printTree() {
        System.out.println("Representación en árbol (niveles):");
        int nivel = 0;
        int count = 0;
        for (int i = 0; i < size; i++) {
            if (count == 0) {
                // nuevo nivel
                System.out.print("");
            }
            System.out.print(heap[i]);
            count++;
            if (count == Math.pow(2, nivel)) {
                System.out.println();
                nivel++;
                count = 0;
            } else {
                System.out.print(" ");
            }
        }
        if (count != 0) System.out.println();
    }

    // Ejercicio 6: heapsort (método estático que usa MinHeap)
    public static void heapsort(int[] arr) {
        System.out.println("\n--- Heapsort usando MinHeap ---");
        MinHeap h = new MinHeap();
        System.out.println("Insertando elementos en el heap:");
        for (int v : arr) h.add(v);
        System.out.println("Extrayendo en orden ascendente:");
        for (int i = 0; i < arr.length; i++) {
            arr[i] = h.poll();
        }
        System.out.println("Arreglo ordenado: " + Arrays.toString(arr));
    }

    // Nota: demos y clases auxiliares (ej. MaxHeap) se han movido a MinHeapDemo.java

    // utilidades
    private void ensureCapacity() {
        if (size >= heap.length) {
            heap = Arrays.copyOf(heap, heap.length * 2);
        }
    }

    private void swap(int i, int j) {
        int t = heap[i];
        heap[i] = heap[j];
        heap[j] = t;
    }

    private int parent(int i) { return (i - 1) / 2; }
    private int leftChild(int i) { return 2 * i + 1; }
    private int rightChild(int i) { return 2 * i + 2; }

    // devuelve una copia sólo de la porción usada para evitar mostrar elementos no inicializados
    private int[] getInternalArraySnapshot() {
        return Arrays.copyOf(heap, size);
    }
}

package pila_cola;

public class ColaArreglo<T> {
    private T[] arreglo;   // almacena los elementos
    private int frente;    // índice del primer elemento
    private int fin;       // índice del último elemento
    private int cantidad;  // cantidad de elementos actuales
    private int capacidad; // tamaño máximo de la cola

    // Constructor
    @SuppressWarnings("unchecked")
    public ColaArreglo(int capacidad) {
        this.capacidad = capacidad;
        this.arreglo = (T[]) new Object[capacidad]; // cast necesario
        this.frente = 0;
        this.fin = -1;
        this.cantidad = 0;
    }

    // Encolar (agregar un elemento al final)
    public void enqueue(T dato) {
        if (isFull()) {
            System.out.println("Error: la cola está llena, no se puede agregar " + dato);
        } else {
            fin = (fin + 1) % capacidad;
            arreglo[fin] = dato;
            cantidad++;
        }
    }

    // Desencolar (sacar el primer elemento)
    public T dequeue() {
        if (isEmpty()) {
            System.out.println("Error: la cola está vacía");
            return null;
        } else {
            T valor = arreglo[frente];
            frente = (frente + 1) % capacidad;
            cantidad--;
            return valor;
        }
    }

    // Consultar el primer elemento sin sacarlo
    public T top() {
        if (isEmpty()) {
            System.out.println("Error: la cola está vacía");
            return null;
        } else {
            return arreglo[frente];
        }
    }

    // Verificar si la cola está vacía
    public boolean isEmpty() {
        return cantidad == 0;
    }

    // Verificar si la cola está llena
    public boolean isFull() {
        return cantidad == capacidad;
    }

    // Mostrar la cola
    public void mostrar() {
        if (isEmpty()) {
            System.out.println("Pila: vacía");
        } else {
            System.out.print("Cola: ");
            for (int i = 0; i < cantidad; i++) {
                int indice = (frente + i) % capacidad;
                System.out.print(arreglo[indice] + ", ");
            }
            System.out.println();
        }
    }
    public void size() {
        System.out.println(cantidad);
    }
}

package pila_cola;
public class PilaArreglo<T> {
    private T[] arreglo;   // almacena los elementos
    private int tope;        // índice del último elemento
    private int capacidad;   // tamaño máximo de la pila

    // Constructor
    @SuppressWarnings("unchecked")
    public PilaArreglo(int capacidad) {
        this.capacidad = capacidad;
        this.arreglo = (T[]) new Object[capacidad];
        this.tope = -1; // la pila empieza vacía
    }

    // Agregar un elemento a la pila
    public void push(T dato) {
        if (isFull()) {
            System.out.println("Error: la pila está llena, no se puede agregar " + dato);
        } else {
            arreglo[++tope] = dato; // incrementa y asigna
        }
    }

    // Sacar un elemento de la pila
    public T pop() {
        if (isEmpty()) {
            System.out.println("Error: la pila está vacía");
            return null; // valor por defecto
        } else {
            return arreglo[tope--]; // devuelve y decrementa
        }
    }

    // Consultar el último elemento sin sacarlo
    public T top() {
        if (isEmpty()) {
            System.out.println("Error: la pila está vacía");
            return null;
        } else {
            return arreglo[tope];
        }
    }

    // Verificar si la pila está vacía
    public boolean isEmpty() {
        return tope == -1;
    }

    // Verificar si la pila está llena
    public boolean isFull() {
        return tope == capacidad - 1;
    }

    // Método extra (mostrar pila)
    public void mostrar() {
        if (isEmpty()) {
            System.out.println("Pila: vacía");
        } else {
            System.out.print("Pila: ");
            for (int i = 0; i <= tope; i++) {
                System.out.print(arreglo[i] + ", ");
            }
            System.out.println();
        }
    }

    public void escribirPalabraInvertida() {
        System.out.print("Palabra invertida: ");
        while(!isEmpty()) {
            System.out.print(pop());
        }
        System.out.println();
    }
}

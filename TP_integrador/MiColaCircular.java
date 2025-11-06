public class MiColaCircular<T> {
    private T[] array;
    private final int capacidad;
    private int front = 0;
    private int rear = 0;
    private int size = 0;

    @SuppressWarnings("unchecked")
    public MiColaCircular(int capacidad) {
        if (capacidad <= 0) throw new IllegalArgumentException("capacidad debe ser > 0");
        this.capacidad = capacidad;
        this.array = (T[]) new Object[capacidad];
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == capacidad;
    }

    public T peek() {
        if (isEmpty()) return null;
        return array[front];
    }

    /**
     * Llega un nuevo dato a la cola.
     * Si la cola está llena, pisa al más antiguo (sobrescribe y avanza front y rear, size no cambia).
     * Si no está llena, añade al rear y aumenta size.
     */
    public void llega(T dato) {
        if (isFull()) {
            // sobrescribir al más antiguo
            array[rear] = dato;
            rear = (rear + 1) % capacidad;
            front = (front + 1) % capacidad; // el más antiguo fue pisado
        } else {
            array[rear] = dato;
            rear = (rear + 1) % capacidad;
            size++;
        }
    }

    /**
     * Atiende (dequeue): retorna null si está vacía, sino retorna el elemento en front y actualiza punteros.
     */
    public T atiende() {
        if (isEmpty()) return null;
        T dato = array[front];
        array[front] = null; // evitar memory leak
        front = (front + 1) % capacidad;
        size--;
        return dato;
    }

    /**
     * Indica si la cola contiene el elemento dado (usa equals).
     */
    public boolean contains(T dato) {
        if (dato == null) return false;
        for (int i = 0, idx = front; i < size; i++, idx = (idx + 1) % capacidad) {
            T v = array[idx];
            if (dato.equals(v)) return true;
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("MiColaCircular[");
        for (int i = 0, idx = front; i < size; i++, idx = (idx + 1) % capacidad) {
            if (i > 0) sb.append(", ");
            sb.append(array[idx]);
        }
        sb.append("]");
        return sb.toString();
    }
}

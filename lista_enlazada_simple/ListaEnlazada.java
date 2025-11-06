package lista_enlazada_simple;

public class ListaEnlazada<T> {
    private Nodo<T> cabeza; // primer nodo de la lista

    public ListaEnlazada() {
        this.cabeza = null;
    }

    // Insertar al inicio
    public void insertarInicio(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        nuevo.setSiguiente(cabeza);
        cabeza = nuevo;
    }

    // Insertar al final
    public void insertarFinal(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            Nodo<T> aux = cabeza;
            while (aux.getSiguiente() != null) {
                aux = aux.getSiguiente();
            }
            aux.setSiguiente(nuevo);
        }
    }

    // Eliminar por valor (el primero que coincida)
    public void eliminar(T valor) {
        if (cabeza == null) return;

        if (cabeza.getDato().equals(valor)) {
            cabeza = cabeza.getSiguiente();
            return;
        }

        Nodo<T> actual = cabeza;
        while (actual.getSiguiente() != null && 
               !actual.getSiguiente().getDato().equals(valor)) {
            actual = actual.getSiguiente();
        }

        if (actual.getSiguiente() != null) {
            actual.setSiguiente(actual.getSiguiente().getSiguiente());
        }
    }

    // Buscar un valor
    public boolean buscar(T valor) {
        Nodo<T> aux = cabeza;
        while (aux != null) {
            if (aux.getDato().equals(valor)) return true;
            aux = aux.getSiguiente();
        }
        return false;
    }

    // Contar elementos
    public int contar() {
        int count = 0;
        Nodo<T> aux = cabeza;
        while (aux != null) {
            count++;
            aux = aux.getSiguiente();
        }
        return count;
    }

    // Invertir la lista
    public void invertir() {
        Nodo<T> prev = null;
        Nodo<T> actual = cabeza;
        Nodo<T> siguiente;

        while (actual != null) {
            siguiente = actual.getSiguiente();
            actual.setSiguiente(prev);
            prev = actual;
            actual = siguiente;
        }
        cabeza = prev;
    }

    // Insertar en posición (0 = inicio)
    public void insertarEn(int pos, T dato) {
        int n = contar();

        if (pos < 0 || pos > n) {
            System.out.println("Posición inválida. Ingrese un valor entre 0 y " + n);
            return;
        }

        if (pos == 0) {
            insertarInicio(dato);
            return;
        }

        Nodo<T> nuevo = new Nodo<>(dato);
        Nodo<T> aux = cabeza;
        int i = 0;

        while (aux != null && i < pos - 1) {
            aux = aux.getSiguiente();
            i++;
        }

        nuevo.setSiguiente(aux.getSiguiente());
        aux.setSiguiente(nuevo);
    }


    // Eliminar duplicados
    public void eliminarDuplicados() {
        Nodo<T> actual = cabeza;
        while (actual != null) {
            Nodo<T> runner = actual;
            while (runner.getSiguiente() != null) {
                if (actual.getDato().equals(runner.getSiguiente().getDato())) {
                    runner.setSiguiente(runner.getSiguiente().getSiguiente());
                } else {
                    runner = runner.getSiguiente();
                }
            }
            actual = actual.getSiguiente();
        }
    }

    // Mostrar lista
    public void imprimir() {
        Nodo<T> aux = cabeza;
        while (aux != null) {
            System.out.print(aux.getDato() + " -> ");
            aux = aux.getSiguiente();
        }
        System.out.println("null");
    }
}

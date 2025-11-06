import java.util.ArrayList;

public class MiMinHeap<T extends Comparable<T>> {
    private ArrayList<T> list;

    public MiMinHeap() {
        this.list = new ArrayList<>();
    }

    private int getPadre(int i) {
        return (i - 1) / 2;
    }

    private int getHijoIzq(int i) {
        return 2 * i + 1;
    }

    private int getHijoDer(int i) {
        return 2 * i + 2;
    }

    private void swap(int i, int j) {
        T tmp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, tmp);
    }

    public void insert(T dato) {
        list.add(dato);
        swim(list.size() - 1);
    }

    private void swim(int index) {
        while (index > 0) {
            int padre = getPadre(index);
            T current = list.get(index);
            T p = list.get(padre);
            if (current.compareTo(p) < 0) {
                swap(index, padre);
                index = padre;
            } else {
                break;
            }
        }
    }

    public T extractMin() {
        if (list.isEmpty()) return null;
        T min = list.get(0);
        int lastIndex = list.size() - 1;
        if (lastIndex == 0) {
            list.remove(lastIndex);
            return min;
        }
        list.set(0, list.get(lastIndex));
        list.remove(lastIndex);
        sink(0);
        return min;
    }

    private void sink(int index) {
        int size = list.size();
        while (true) {
            int left = getHijoIzq(index);
            int right = getHijoDer(index);
            int smallest = index;

            if (left < size && list.get(left).compareTo(list.get(smallest)) < 0) {
                smallest = left;
            }
            if (right < size && list.get(right).compareTo(list.get(smallest)) < 0) {
                smallest = right;
            }

            if (smallest != index) {
                swap(index, smallest);
                index = smallest;
            } else {
                break;
            }
        }
    }

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    // Método auxiliar para ver el contenido (útil para pruebas)
    public String toString() {
        return list.toString();
    }
}

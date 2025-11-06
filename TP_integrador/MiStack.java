import java.util.ArrayList;
import java.util.EmptyStackException;

public class MiStack<T> {
    private final ArrayList<T> list;

    public MiStack() {
        this.list = new ArrayList<>();
    }

    public void push(T dato) {
        list.add(dato);
    }

    public T pop() {
        if (list.isEmpty()) {
            throw new EmptyStackException();
        }
        return list.remove(list.size() - 1);
    }

    public T peek() {
        if (list.isEmpty()) {
            throw new EmptyStackException();
        }
        return list.get(list.size() - 1);
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public int size() {
        return list.size();
    }

    @Override
    public String toString() {
        return list.toString();
    }
}

package arbolrojinegro;

/**
 * RBNode genérico para el árbol rojinegro.
 */
public class RBNode<K extends Comparable<K>, V> {
    public enum Color { ROJO, NEGRO }

    public K key;
    public V val;
    public Color color;
    public RBNode<K,V> left, right, parent;

    // Constructor general (se puede pasar nil para inicializar hijos/padre)
    public RBNode(K key, V val, Color color, RBNode<K,V> nil) {
        this.key = key;
        this.val = val;
        this.color = color;
        if (nil != null) {
            this.left = nil;
            this.right = nil;
            this.parent = nil;
        } else {
            this.left = this.right = this.parent = null;
        }
    }

    // Constructor para crear NIL o nodos sin pasar nil
    public RBNode(K key, V val, Color color) {
        this(key, val, color, null);
    }

    @Override
    public String toString() {
        return String.format("(%s:%s,%s)", key, val, color == Color.ROJO ? "R" : "N");
    }
}

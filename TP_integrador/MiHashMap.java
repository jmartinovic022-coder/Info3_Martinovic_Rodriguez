public class MiHashMap<K, V> {
    // Clase estática anidada Entry para los nodos de la lista enlazada
    private static class Entry<K, V> {
        K key;
        V value;
        Entry<K, V> next;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }
    }

    public int size() {
        return this.size;
    }

    /**
     * Retorna una lista con todos los valores almacenados en el mapa.
     */
    public java.util.List<V> values() {
        java.util.List<V> out = new java.util.ArrayList<>();
        for (Entry<K, V> bucket : table) {
            Entry<K, V> current = bucket;
            while (current != null) {
                out.add(current.value);
                current = current.next;
            }
        }
        return out;
    }

    // Tabla hash implementada como un array de Entry
    private Entry<K, V>[] table;
    private int size;
    private final double loadFactor = 0.75;

    @SuppressWarnings("unchecked")
    public MiHashMap(int initialCapacity) {
        // Creamos la tabla con la capacidad inicial especificada
        this.table = (Entry<K, V>[]) new Entry[initialCapacity];
        this.size = 0;
    }

    public void put(K key, V value) {
        int bucketIndex = Math.abs(key.hashCode()) % table.length;
        Entry<K, V> head = table[bucketIndex];
        Entry<K, V> current = head;
        while (current != null) {
            if (current.key.equals(key)) {
                current.value = value;
                return;
            }
            current = current.next;
        }
        Entry<K, V> newEntry = new Entry<>(key, value);
        newEntry.next = head;
        table[bucketIndex] = newEntry;
        size++;
        // Check load factor and rehash if necessary
        if ((double) size / table.length > loadFactor) {
            rehash();
        }
    }

    public V get(K key) {
        int bucketIndex = Math.abs(key.hashCode()) % table.length;
        Entry<K, V> current = table[bucketIndex];
        while (current != null) {
            if (current.key.equals(key)) {
                return current.value;
            }
            current = current.next;
        }
        return null;
    }

    public boolean remove(K key) {
        int bucketIndex = Math.abs(key.hashCode()) % table.length;
        Entry<K, V> current = table[bucketIndex];
        Entry<K, V> prev = null;
        while (current != null) {
            if (current.key.equals(key)) {
                if (prev == null) {
                    // removing head
                    table[bucketIndex] = current.next;
                } else {
                    prev.next = current.next;
                }
                size--;
                return true;
            }
            prev = current;
            current = current.next;
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    private void rehash() {
        Entry<K, V>[] oldTable = table;
        // Create new table with double capacity
        table = (Entry<K, V>[]) new Entry[oldTable.length * 2];
        // Reset size and re-insert entries
        int oldSize = size;
        size = 0;
        for (int i = 0; i < oldTable.length; i++) {
            Entry<K, V> current = oldTable[i];
            while (current != null) {
                put(current.key, current.value);
                current = current.next;
            }
        }
        // size should equal oldSize after reinsertion
        // (we don't strictly need to set it here because put increments size)
    }
}